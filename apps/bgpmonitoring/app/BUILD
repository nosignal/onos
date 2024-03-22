COMPILE_DEPS = CORE_DEPS + JACKSON + METRICS + KRYO + CLI + REST + [
    "//apps/bgpmonitoring/api:onos-apps-bgpmonitoring-api",
    "//protocols/bgp/bgpio:onos-protocols-bgp-bgpio",
    "//protocols/bgp/api:onos-protocols-bgp-api",
    "@io_netty_netty_common//jar",
    "@io_netty_netty//jar",
]

osgi_jar(
    deps = COMPILE_DEPS,
)
