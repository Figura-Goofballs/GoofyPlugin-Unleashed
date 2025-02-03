FROM alpine AS rustup
RUN apk add clang
ADD https://sh.rustup.rs rustup.sh
RUN chown 1000 rustup.sh
RUN . rustup.sh -y --target x86_64-unknown-linux-musl

FROM rustup AS files
WORKDIR /build
ADD . .

FROM files AS build
ENV RUSTFLAGS="-C target-feature=+crt-static"
RUN /root/.cargo/bin/cargo build --release --target x86_64-unknown-linux-musl

FROM build AS build-doc
RUN cargo doc

FROM alpine
COPY --from=build /build/target/x86_64-unknown-linux-musl/release/backend /exe
ENTRYPOINT ["/exe"]
