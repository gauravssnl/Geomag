#include <jni.h>
#include <cstdlib>
#include "geomag.h"
#include "libwmm.h"
#include "logging.h"

MagneticField wmm(double lat, double lon, double alt, double decimal_years) {
    double *values = WMM(lat, lon, alt, decimal_years);

    MagneticField results = {};
    MagneticField* results_t = &results;

    double *item = &results_t -> Declination;
    for (int i = 0; i < 14; i++) {
        double value = values[i];
        if (isnan(value)) {
            (*item++) = 0.0;
        } else {
            (*item++) = value;
        }
    }

    return results;
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_sanmer_geomag_core_models_Geomag_wmm(JNIEnv *env, jobject thiz, jdouble latitude,
                                              jdouble longitude, jdouble alt_km) {
    jclass cls = (*env).GetObjectClass(thiz);
    jfieldID dy_id = (*env).GetStaticFieldID(cls, "decimalYears", "D");
    jdouble dy = (*env).GetStaticDoubleField(cls, dy_id);

    LOGD("WMM lat=%f, lon=%f, alt_km=%f, date=%f", latitude, longitude, alt_km, dy);
    MagneticField values = wmm(latitude, longitude, alt_km, dy);
    return GEOMAG::toMagneticField(env, values);
}