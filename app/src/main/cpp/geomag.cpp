#include <jni.h>
#include "geomag.h"
#include "libwmm.h"

namespace GEOMAG {
    jobject toMagneticField(JNIEnv *env, MagneticField values) {
        jclass mf_clazz = (*env).FindClass("com/sanmer/geomag/model/MagneticField");
        jmethodID mf_init = (*env).GetMethodID(mf_clazz, "<init>", "(DDDDDDDDDDDDDD)V");
        jobject mf_object = (*env).NewObject(mf_clazz, mf_init,
                    values.Declination, values.DeclinationSV,
                    values.Inclination, values.InclinationSV,
                    values.HorizontalIntensity, values.HorizontalSV,
                    values.NorthComponent, values.NorthSV,
                    values.EastComponent, values.EastSV,
                    values.VerticalComponent, values.VerticalSV,
                    values.TotalIntensity, values.TotalSV
            );

        return mf_object;
    }

    double getDecimalYears(int year, int month, int day, int hour, int min, int sec) {
        return toDecimalYears(year, month, day, hour, min, sec);
    }
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_sanmer_geomag_Geomag_toDecimalYears(JNIEnv *env, jobject thiz, jint year,
                                                         jint month, jint day, jint hour, jint min,
                                                         jint sec) {
    return GEOMAG::getDecimalYears(year, month, day, hour, min, sec);
}