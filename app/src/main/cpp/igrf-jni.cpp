#include <jni.h>
#include "geomag.h"
#include "libigrf.h"
#include "logging.h"

MagneticField I_MF;

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
JNIEXPORT void JNICALL
Java_com_sanmer_geomag_core_models_IGRF_setDateTime(JNIEnv *env, jobject thiz, jint year, jint month, jint day,
                                                    jint hour, jint min, jint sec) {
    double decimal_years = GEOMAG::getDecimalYears(year, month, day, hour, min, sec);

    jclass cls = (*env).GetObjectClass(thiz);
    jmethodID set_dy = (*env).GetMethodID(cls, "setDecimalYears", "(D)V");
    (*env).CallVoidMethod(thiz, set_dy, decimal_years);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_sanmer_geomag_core_models_IGRF_igrf(JNIEnv *env, jobject thiz, jdouble latitude,
                                              jdouble longitude, jdouble alt_km) {
    jclass cls = (*env).GetObjectClass(thiz);
    jfieldID dy_id = (*env).GetStaticFieldID(cls, "decimalYears", "D");
    jdouble dy = (*env).GetStaticDoubleField(cls, dy_id);

    LOGD("IGRF lat=%f, lon=%f, alt_km=%f, date=%f", latitude, longitude, alt_km, dy);
    I_MF = igrf(latitude, longitude, alt_km, dy);
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_sanmer_geomag_core_models_IGRF_getMF(JNIEnv *env, jobject thiz) {
    return GEOMAG::toMagneticField(env, I_MF);
}