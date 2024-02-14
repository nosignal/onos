COMPILE_DEPS = CORE_DEPS + KRYO + CLI + [
    "//core/store/serializers:onos-core-serializers",
    "//apps/ipflow-monitor/netflow/api:onos-apps-ipflow-monitor-netflow-api",
    "@io_netty_netty_common//jar",
    "@io_netty_netty//jar",
]

osgi_jar(
    karaf_command_packages = ["org.onosproject.netflow.cli"],
    deps = COMPILE_DEPS,
)
