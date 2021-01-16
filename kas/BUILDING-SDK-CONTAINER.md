# Containerized SDK

## Building

Build Bitbake task `populate_sdk` for the target `iot2050-image-base` with kas-container using a `kas-isar` container image created as PoC installing `umoci` and `skopeo`.
Go get a coffee, this build takes around 40min in my machine!

```
KAS_CONTAINER_IMAGE_PATH="ghcr.io/silvanoc/kas" \
KAS_IMAGE_VERSION="next" \
    kas-container \
        --isar build \
        -c populate_sdk \
        kas/iot2050.yml
```

The archive `build/tmp/deploy/images/iot2050/sdk-isar-arm64-docker-archive.tar.xz` contains the Docker container image archive.

## Running the SDK

Load the Docker container image on the local Docker daemon:

```
xzcat build/tmp/deploy/images/iot2050/sdk-isar-arm64-docker-archive.tar.xz | docker load
```

Run a container using the SDK container image:

```
docker run --rm -ti --volume $(pwd):/iot2050 isar-sdk-isar-arm64
```

Check that cross toolchains are installed (inside of the container)

```
dpkg -l | grep crossbuild-essential-arm64
```

Check that binfmt works getting umoci for ARM64

```
apt-update
apt-get install umoci:arm64 jq
file $(which umoci)
```

Check that we can even prepare an ARM64 OCIv2 container image

```
umoci init --layout /arm64-container-image
umoci new --image /arm64-container-image:latest
image_manifest=$(jq -r .manifests[0].digest /arm64-container-image/index.json | sed 's/:/\//')
image_config=$(jq -r .config.digest /arm64-container-image/blobs/${image_manifest} | sed 's/:/\//')
image_architecture=$(jq -r .architecture /arm64-container-image/blobs/${image_config})
echo "You've just prepared a OCIv2 container image for '${image_architecture}'"
```

If the output reads

```
You've just prepared a OCIv2 container image for 'arm64'
```

🎉 you have a working SDK container!

