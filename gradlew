#!/usr/bin/env sh
# Lightweight Gradle launcher for GitHub Actions.
# The workflow installs Gradle via gradle/actions/setup-gradle, then this script delegates to it.
set -e
exec gradle "$@"
