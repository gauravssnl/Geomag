#include <jni.h>
#include "geomag.h"
#include "libigrf.h"
#include "logging.h"

MagneticField igrf(double lat, double lon, double alt, double decimal_years) {
    double *values = IGRF(lat, lon, alt, decimal_years);

    MagneticField results = {};
    MagneticField* results_t = &results;

    double *item = &results_t -> Declination;
    for (int i = 0; i < 14; i++) {
        (*item++) = values[i];
    }

    return results;
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_sanmer_geomag_Geomag_igrf(JNIEnv *env, jobject thiz, jdouble latitude,
                                               jdouble longitude, jdouble alt_km) {
    jclass cls = (*env).GetObjectClass(thiz);
    jfieldID dy_id = (*env).GetStaticFieldID(cls, "decimalYears", "D");
    jdouble dy = (*env).GetStaticDoubleField(cls, dy_id);

    LOGD("IGRF lat=%f, lon=%f, alt_km=%f, date=%f", latitude, longitude, alt_km, dy);
    MagneticField values = igrf(latitude, longitude, alt_km, dy);
    return GEOMAG::toMagneticField(env, values);
}