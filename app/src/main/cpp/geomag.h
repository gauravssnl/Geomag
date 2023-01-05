#ifndef GEOMAG_H
#define GEOMAG_H

struct MagneticField {
    double Declination, DeclinationSV;
    double Inclination, InclinationSV;
    double HorizontalIntensity, HorizontalSV;
    double NorthComponent, NorthSV;
    double EastComponent, EastSV;
    double VerticalComponent, VerticalSV ;
    double TotalIntensity, TotalSV;
};

namespace GEOMAG {
    double getDecimalYears(int year, int month, int day, int hour, int min, int sec);
    jobject toMagneticField(JNIEnv *env, MagneticField values);
}

#endif //GEOMAG_H
