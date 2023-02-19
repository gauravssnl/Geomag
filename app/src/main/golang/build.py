#!/usr/bin/env python3

import os
import platform
import subprocess
import argparse
from pathlib import Path


def parse_parameters():
    parser = argparse.ArgumentParser()

    parser.add_argument("-v",
                        dest="ndk_version",
                        metavar="NDK version",
                        type=str,
                        default="25.2.9519653",
                        help="default: {0}".format('%(default)s'))
    parser.add_argument("-m",
                        dest="min_sdk",
                        metavar="Min SDK version",
                        type=int,
                        default=26,
                        help="default: {0}".format('%(default)s'))
    parser.add_argument("-a",
                        dest="abi_filters",
                        metavar="ABI",
                        nargs="+",
                        default=[],
                        help="arm64-v8a, armeabi-v7a, x86, x86_64 or all")
    parser.add_argument("-d",
                        "--debug",
                        action="store_true",
                        help="enable debug mode to output compilation log")
    return parser


class BuildTask:
    def __init__(self, ndk_directory: Path, work_directory: Path, output_directory: Path,
                 abi_filters: list, package_names: list, library_names: list, sdk_version: int):

        setattr(self, "_ndk_directory", ndk_directory)
        setattr(self, "_work_directory", work_directory)
        setattr(self, "_output_directory", output_directory)

        self._abi_filters = abi_filters
        self._package_names = package_names
        self._library_names = library_names

        self._sdk_version = sdk_version

        self._is_debug = False

    def __getattr__(self, item):
        return self.__dict__[item]

    @property
    def is_debug(self):
        return self._is_debug

    @is_debug.setter
    def is_debug(self, value: bool):
        self._is_debug = value

    @staticmethod
    def is_windows_x86_64() -> bool:
        if platform.system() == "Windows":
            if platform.machine() == "x86_64":
                return True

        return False

    @staticmethod
    def is_darwin_x86_64() -> bool:
        if platform.system() == "Darwin":
            if platform.machine() == "x86_64":
                return True

        return False

    @staticmethod
    def is_linux_x86_64() -> bool:
        if platform.system() == "Linux":
            if platform.machine() == "x86_64":
                return True

        return False

    def build_environment(self, abi: str) -> dict:
        toolchains_root = self._ndk_directory.absolute()
        toolchains_root = toolchains_root.joinpath("toolchains", "llvm", "prebuilt")

        if self.is_darwin_x86_64():
            toolchains_root = toolchains_root.joinpath("darwin-x86_64")
        elif self.is_linux_x86_64():
            toolchains_root = toolchains_root.joinpath("linux-x86_64")
        elif self.is_windows_x86_64():
            toolchains_root = toolchains_root.joinpath("windows-x86_64")
        else:
            raise OSError(f"Unsupported platform: {platform.system()}-{platform.machine()}")

        toolchains_root = toolchains_root.joinpath("bin")

        match abi:
            case "arm64-v8a":
                compiler_prefix = "aarch64-linux-android"
                go_arch = "arm64"
                go_arm = ""
            case "armeabi-v7a":
                compiler_prefix = "armv7a-linux-androideabi"
                go_arch = "arm"
                go_arm = "7"
            case "x86":
                compiler_prefix = "i686-linux-android"
                go_arch = "386"
                go_arm = ""
            case "x86_64":
                compiler_prefix = "x86_64-linux-android"
                go_arch = "amd64"
                go_arm = ""
            case _:
                raise ValueError(f"Unsupported abi: {abi}")

        toolchains_root = toolchains_root.joinpath(f"{compiler_prefix}{self._sdk_version}-clang")

        environment = dict()
        environment["CC"] = toolchains_root.as_posix()
        environment["GOOS"] = "android"
        environment["GOARCH"] = go_arch
        environment["GOARM"] = go_arm
        environment["CGO_ENABLED"] = "1"
        environment["CFLAGS"] = "-O3 -Werror"

        return environment

    def build(self, package_name: str, library_name: str, abi: str):
        commands = list()
        commands.append("go")
        commands.append("build")

        if self._is_debug:
            commands.append("-x")

        commands.append("-trimpath")
        commands.append("-buildmode")
        commands.append("c-shared")
        commands.append("-o")

        commands.append(self._output_directory.joinpath(abi, f"lib{library_name}.so").as_posix())

        commands.append("-ldflags")
        commands.append("-s -w")

        commands.append(package_name)

        print(f"> Task :{library_name}:configureEnvironment[{abi}]")
        env = os.environ.update(self.build_environment(abi))

        print(f"> Task :{library_name}:buildLibrary[{abi}]")
        subprocess.run(
            args=commands,
            cwd=self._work_directory.as_posix(),
            env=env
        )

    def task(self):
        for abi in self._abi_filters:
            for i in range(0, len(self._package_names)):
                package_name = self._package_names[i]
                library_name = self._library_names[i]

                self.build(
                    package_name=package_name,
                    library_name=library_name,
                    abi=abi
                )


def main():
    parser = parse_parameters()
    args = parser.parse_args()

    ndk_version = args.ndk_version
    sdk_version = args.min_sdk

    if len(args.abi_filters) == 0:
        parser.print_help()

    if "all" in args.abi_filters:
        abi_filters = ["arm64-v8a", "armeabi-v7a", "x86", "x86_64"]
    else:
        abi_filters = args.abi_filters

    # libs
    package_names = ["geomag/igrf", "geomag/wmm"]
    library_names = ["igrf", "wmm"]

    # dirs
    sdk_dir = Path(os.environ["ANDROID_SDK_ROOT"])
    ndk_directory = sdk_dir.joinpath("ndk", ndk_version)
    root_directory = Path(__file__).resolve().parent

    build = BuildTask(
        abi_filters=abi_filters,
        package_names=package_names,
        library_names=library_names,
        sdk_version=sdk_version,
        ndk_directory=ndk_directory,
        work_directory=root_directory,
        output_directory=root_directory.joinpath("libs")
    )
    build.is_debug = args.debug
    build.task()


if __name__ == "__main__":
    main()
