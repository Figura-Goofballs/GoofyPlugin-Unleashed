FROM nixos/nix
MAINTAINER PoolloverNathan
COPY <<EOT /etc/nix/nix.conf
sandbox = true
build-users-group = nixbld
extra-experimental-features = nix-command flakes
extra-substituters = https://cache.pool.net.eu.org
extra-trusted-public-keys = cache.pool.net.eu.org:7tDwL5h6E8zCvdjIb3gDf7ScjLTKjOnLvsbqQJa9TM8=
builders = ssh://nathanlaptopv.axolotl-snake.ts.net?base64-ssh-public-host-key=AAAAC3NzaC1lZDI1NTE5AAAAILEMN/0ksFxAEQlwRmTc6+kb1+S4Su7W3JljT+rcIiIV
EOT
COPY . src
RUN --network=host ["/root/.nix-profile/bin/nix", "build", "-vvv", "./src", "--accept-flake-config", "--log-format", "raw"]
RUN --network=none ["/root/.nix-profile/bin/cp", "result/bin/backend", "/"]

FROM scratch
MAINTAINER PoolloverNathan
COPY --from=0 /backend /backend
ENTRYPOINT ["/backend"]