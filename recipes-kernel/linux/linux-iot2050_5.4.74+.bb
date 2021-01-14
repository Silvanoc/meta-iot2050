#
# Copyright (c) Siemens AG, 2020
#
# Authors:
#  Chao Zeng <chao.zeng@siemens.com>
#
# This file is subject to the terms and conditions of the MIT License.  See
# COPYING.MIT file in the top-level directory.
#

require recipes-kernel/linux/linux-custom.inc

SRC_URI += " \
    https://git.ti.com/cgit/ti-linux-kernel/ti-linux-kernel/snapshot/ti-linux-kernel-${KERNEL_REV}.tar.xz;protocol=https; \
    file://5.x-patch/0001-iot2050-add-iot2050-platform-support.patch \
    file://5.x-patch/0002-Add-support-for-U9300C-TD-LTE-module.patch \
    file://5.x-patch/0003-feat-Add-CP210x-driver-support-to-software-flow-cont.patch \
    file://5.x-patch/0004-setting-the-RJ45-port-led-behavior.patch \
    file://5.x-patch/0005-feat-extend-led-panic-indicator-on-and-off.patch \
    file://5.x-patch/0006-feat-change-mmc-order-using-alias-in-dts.patch \
    file://5.x-patch/0008-feat-add-io-expander-pcal9535-support.patch \
    file://5.x-patch/0009-add-the-gpio-alias-support.patch"

SRC_URI += " \
    file://${KERNEL_DEFCONFIG} \
    file://iot2050-upstream.cfg \
    file://iot2050_defconfig_extra.cfg"

KERNEL_REV = "9574bba32a1898794895ca3816e815154c80226d"
KERNEL_DEFCONFIG = "iot2050_defconfig_base"
SRC_URI[sha256sum] = "59fc1e9ec4af575837a3afe30e031b9f689b759b87c8414099dd05579a9e7b3d"

S = "${WORKDIR}/ti-linux-kernel-${KERNEL_REV}"
