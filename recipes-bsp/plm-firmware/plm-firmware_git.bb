DESCRIPTION = "PLM Firmware"
SUMMARY = "PLM firmware for versal devices"

PROVIDES = "virtual/plm-firmware"

inherit xsctapp xsctyaml deploy

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE_versal = "versal"

XSCTH_PROC_versal = "psv_pmc_0"
XSCTH_APP   = "versal PLM"

INSANE_SKIP_${PN} = "arch"
INSANE_SKIP_${PN}-dbg = "arch"

