COMPILE_DEPS = CORE_DEPS + KRYO + CLI + [
    "//core/store/serializers:onos-core-serializers",
    "//apps/ipflow-monitor/sflow/api:onos-apps-ipflow-monitor-sflow-api",
    "@io_netty_netty_common//jar",
    "@io_netty_netty//jar",
]

osgi_jar(
    deps = COMPILE_DEPS,
)
