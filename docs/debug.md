# Debug

To enable breakpoints, there are a few simple steps to get started.

1. On the remote server, run the jar with the following parameters. 
`-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:25566`
2. In intellij, setup a `remote JVM debug` with the `host` to your server and port `25566`. 
3. In your minecraft server base directory, edit `spigot.yml` and change the `timeout-time` to `999999`