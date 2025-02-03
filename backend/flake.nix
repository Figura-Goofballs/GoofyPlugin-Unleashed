{
  inputs = {
    rust-flake.url = github:poollovernathan/rust-flake;
  };
  outputs = { self, rust-flake }: {
    packages = rust-flake.lib.perSystem (pkgs: {
      default = rust-flake.lib.crossCompile' {
        inherit pkgs;
        src = ./.;
        target = null;
      };
    });
  };
  nixConfig = {
    extra-substituters = https://cache.pool.net.eu.org;
    extra-trusted-public-keys = cache.pool.net.eu.org:7tDwL5h6E8zCvdjIb3gDf7ScjLTKjOnLvsbqQJa9TM8=;
    builders = ssh-ng://nathanlaptopv.axolotl-snake.ts.net?base64-ssh-public-host-key=AAAAC3NzaC1lZDI1NTE5AAAAILEMN/0ksFxAEQlwRmTc6+kb1+S4Su7W3JljT+rcIiIV;
  };
}
