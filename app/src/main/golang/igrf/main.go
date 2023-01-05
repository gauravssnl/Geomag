package main

import (
	"C"
	"fmt"
	"github.com/proway2/go-igrf/igrf"
	"reflect"
	"unsafe"
)

//export IGRF
func IGRF(lat, lon, alt, date C.double) *C.double {
	size := 14
	p := C.malloc(C.size_t(size) * C.size_t(unsafe.Sizeof(C.double(0))))
	out := (*[14]C.double)(p)

	res, _ := igrf.IGRF(float64(lat), float64(lon), float64(alt), float64(date))
	values := reflect.ValueOf(res)
	for i := 0; i < 14; i++ {
		v := values.Field(i).Float()
		out[i] = C.double(v)
	}

	return (*C.double)(p)
}

func main() {
	res, _ := igrf.IGRF(24.82, 102.85, 1.945, 2022.0)
	fmt.Println(res)
}
