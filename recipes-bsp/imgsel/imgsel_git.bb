DESCRIPTION = "Image Selector"

PROVIDES = "virtual/imgsel"
DEPENDS += "bootgen-native"

inherit check_xsct_enabled xsctapp xsctyaml deploy

PARALLEL_MAKE = "-j 1"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:zynqmp = "zynqmp"

XSCTH_APP:zynqmp = "Image Selector"

do_configure:append () {
	# Required for SOM only
	sed -i "s|//#define XIS_UPDATE_A_B_MECHANISM|#define XIS_UPDATE_A_B_MECHANISM|g" ${B}/${XSCTH_PROJ}/xis_config.h
	sed -i "s|#define XIS_GET_BOARD_PARAMS|//#define XIS_GET_BOARD_PARAMS|g" ${B}/${XSCTH_PROJ}/xis_config.h

cat > ${WORKDIR}/${PN}.bif << EOF
        the_ROM_image:
        {
                [bootloader,destination_cpu=a53-0] ${B}/${XSCTH_PROJ}/${XSCTH_EXECUTABLE}
        }
EOF
}

do_compile:append () {
        bootgen -image ${WORKDIR}/${PN}.bif -arch ${SOC_FAMILY} -w -o ${B}/${XSCTH_PROJ}/${PN}.bin
}

do_deploy:append () {
        install -Dm 0644 ${B}/${XSCTH_PROJ}/${PN}.bin ${DEPLOYDIR}/${XSCTH_BASE_NAME}.bin
        ln -sf ${XSCTH_BASE_NAME}.bin ${DEPLOYDIR}/${PN}-${MACHINE}.bin
}
