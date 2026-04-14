#!/bin/bash

# Teedy 启动脚本
# 用法: ./start.sh [build|run|all]
#   build - 仅构建项目
#   run   - 仅启动服务（需先构建）
#   all   - 构建并启动（默认）

set -e

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"

# 设置 Java 环境（Homebrew OpenJDK）
export JAVA_HOME="/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"

# 检查依赖
check_deps() {
    echo "=== 检查依赖 ==="
    local missing=0
    for cmd in java mvn npm grunt; do
        if ! command -v "$cmd" &>/dev/null; then
            echo "❌ 未找到: $cmd"
            missing=1
        else
            echo "✅ $cmd: $(command -v "$cmd")"
        fi
    done
    if [ $missing -eq 1 ]; then
        echo "请先安装缺失的依赖。macOS 可使用:"
        echo "  brew install openjdk maven npm grunt-cli tesseract ffmpeg mediainfo"
        exit 1
    fi
    echo ""
}

# 构建项目
build() {
    echo "=== 构建项目 ==="
    cd "$PROJECT_DIR"
    mvn clean -DskipTests install
    echo "✅ 构建完成"
    echo ""
}

# 启动服务
run() {
    echo "=== 启动 Teedy ==="
    echo "访问地址: http://localhost:8080"
    echo "默认账号: admin / admin"
    echo "按 Ctrl+C 停止服务"
    echo ""
    cd "$PROJECT_DIR/docs-web"
    mvn jetty:run
}

# 主入口
ACTION="${1:-all}"

check_deps

case "$ACTION" in
    build)
        build
        ;;
    run)
        run
        ;;
    all)
        build
        run
        ;;
    *)
        echo "用法: $0 [build|run|all]"
        exit 1
        ;;
esac
