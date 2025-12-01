# CrateReloaded

[![Build Status](https://github.com/Hazebyte/CrateReloaded/workflows/CI/badge.svg)](https://github.com/Hazebyte/CrateReloaded/actions)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Version](https://img.shields.io/badge/version-2.3.16-green.svg)](https://github.com/Hazebyte/CrateReloaded/releases)
[![Discord](https://img.shields.io/discord/YOUR_DISCORD_ID?label=Discord&logo=discord)](https://discord.gg/0srgnnU1nbB8wMML)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.8--1.21-orange.svg)](https://www.spigotmc.org/)

A powerful and flexible crate plugin for Minecraft servers. Create customizable crates with animated openings, varied rewards, and a comprehensive claim system. Perfect for Spigot, Paper, and compatible server software.

---

## 📚 Documentation

- **[Wiki](https://github.com/Hazebyte/CrateReloaded/wiki)** - Comprehensive guides and tutorials
- **[API Documentation](https://github.com/Hazebyte/CrateReloadedAPI)** - For plugin developers
- **[Configuration Guide](https://github.com/Hazebyte/CrateReloaded/wiki/Configuration)** - All config options explained
- **[Examples](./examples/)** - Example crate configurations

---

## 🛠️ Development

### Prerequisites

We recommend using [Jabba](https://github.com/shyiko/jabba) as a Java version manager:

```bash
# Install Java 17
jabba install openjdk@1.17
jabba use

# Verify Java version
java -version  # Should show Java 17
```

### Building from Source

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Hazebyte/CrateReloaded.git
   cd CrateReloaded
   ```

2. **Initialize git submodules:**
   ```bash
   ./scripts/init-project.sh
   ```

3. **Build the plugin:**
   ```bash
   # Development build (no obfuscation)
   make build-dev

   # Production build (with ProGuard obfuscation)
   make

   # Just compile
   make compile
   ```

4. **Find the built JAR:**
   - Development: `bukkit/target/CrateReloaded-{version}.jar`
   - Production: `bin/CrateReloaded-{version}.jar` (after ProGuard)

### Running Tests

```bash
# Run all tests
mvn test

# Generate coverage report
mvn test jacoco:report

# Generate full site with reports
mvn site
# View in browser: target/site/index.html
```

### Code Quality

CrateReloaded uses [Spotless](https://github.com/diffplug/spotless) with [Palantir Java Format](https://github.com/palantir/palantir-java-format) for code formatting:

```bash
# Check formatting
make lint

# Auto-fix formatting issues
make lint-fix
```