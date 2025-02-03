use std::net::SocketAddr;
use websocket::r#async::server::*;

#[derive(serde::Deserialize)]
struct Env {
    #[serde(default = "get_default_port")]
    pub bind: SocketAddr,
}

#[derive(Debug, thiserror::Error)]
enum Error {
    #[error("invalid environment: {0}")]
    EnvParseError(envy::Error),
    #[error("failed to bind to {1:?}: {0}")]
    BindFailure(std::io::Error, SocketAddr),
}

fn get_default_port() -> SocketAddr { "127.0.0.1:7990".parse().unwrap() }

#[tokio::main]
async fn main() -> Result<(), Error> {
    let env: Env = envy::from_env().map_err(Error::EnvParseError)?;
    let server = Server::bind(env.bind, &Default::default()).map_err(|e| Error::BindFailure(e, env.bind))?;
    todo!()
}
