package main

import "C"
import (
	"github.com/westphae/geomag/pkg/egm96"
	"github.com/westphae/geomag/pkg/wmm"
	"time"
	"unsafe"
)

//export WMM
func WMM(lat, lon, alt, date C.double) *C.double {
	size := 14
	p := C.malloc(C.size_t(size) * C.size_t(unsafe.Sizeof(C.double(0))))
	out := (*[14]C.double)(p)

	tt := wmm.DecimalYear(float64(date))
	loc := egm96.NewLocationGeodetic(float64(lat), float64(lon), float64(alt))
	mag, _ := wmm.CalculateWMMMagneticField(loc, tt.ToTime())

	x, y, z, dx, dy, dz := mag.Ellipsoidal()

	out[0] = C.double(mag.D())
	out[1] = C.double(mag.DD())
	out[2] = C.double(mag.I())
	out[3] = C.double(mag.DI())

	out[4] = C.double(mag.H())
	out[5] = C.double(mag.DH())

	out[6] = C.double(x)
	out[7] = C.double(dx)
	out[8] = C.double(y)
	out[9] = C.double(dy)
	out[10] = C.double(z)
	out[11] = C.double(dz)

	out[12] = C.double(mag.F())
	out[13] = C.double(mag.DF())

	return (*C.double)(p)
}

//export toDecimalYears
func toDecimalYears(year, month, day, hour, min, sec C.int) C.double {
	dateTime := time.Date(
		int(year), time.Month(int(month)), int(day),
		int(hour), int(min), int(sec), 0, time.UTC)

	decimalYears := wmm.TimeToDecimalYears(dateTime)

	return C.double(decimalYears)
}

func main() {}
