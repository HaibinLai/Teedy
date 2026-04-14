# Practice 7 - PMD 集成总结

## 目标

在 Teedy 项目中集成 PMD Maven 插件，创建自定义规则集覆盖课上讲的 5 个软件度量指标，使 `mvn site` 生成包含 PMD 报告的站点文档。

## 5 个指标与 PMD 规则的映射

| 课上指标 | PMD 规则 | 说明 |
|----------|----------|------|
| **Lines of Code (LoC)** | `NcssCount` | 计算非注释源代码行数（方法级阈值 60，类级阈值 1500） |
| **Cyclomatic Complexity (CC)** | `CyclomaticComplexity` | 方法级 `methodReportLevel=10`，方法复杂度 ≥10 时报告 |
| **Weighted Methods per Class (WMC)** | `CyclomaticComplexity` | 类级 `classReportLevel=80`，类中所有方法 CC 之和 ≥80 时报告 |
| **Coupling Between Objects (CBO)** | `CouplingBetweenObjects` | 统计类中使用的不同类型数量，阈值 20 |
| **Lack of Cohesion in Methods (LCOM)** | `GodClass` | PMD 无直接 LCOM 规则，GodClass 内部使用 TCC（Tight Class Cohesion）度量，是最佳近似 |

> 注意：`CyclomaticComplexity` 一条规则同时覆盖了 CC（方法级）和 WMC（类级）两个指标。

## 实施步骤

### 步骤 1：创建自定义规则集文件

新建 `pmd-rules.xml` 在项目根目录，包含 4 条规则引用：

```xml
<ruleset name="Custom Teedy Ruleset" ...>
    <rule ref="category/java/design.xml/NcssCount" />           <!-- LoC -->
    <rule ref="category/java/design.xml/CyclomaticComplexity" /> <!-- CC + WMC -->
    <rule ref="category/java/design.xml/CouplingBetweenObjects" /> <!-- CBO -->
    <rule ref="category/java/design.xml/GodClass" />             <!-- LCOM -->
</ruleset>
```

### 步骤 2：修改根 `pom.xml`

做了以下 3 处修改（仅修改根 `pom.xml`，子模块无需改动）：

#### 2.1 添加插件版本属性

在 `<properties>` 中添加：

```xml
<org.apache.maven.plugins.maven-pmd-plugin.version>3.21.2</org.apache.maven.plugins.maven-pmd-plugin.version>
```

#### 2.2 添加 build 插件配置

在 `<build><plugins>` 中添加 `maven-pmd-plugin`：

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pmd-plugin</artifactId>
    <version>${org.apache.maven.plugins.maven-pmd-plugin.version}</version>
    <configuration>
        <rulesets>
            <ruleset>pmd-rules.xml</ruleset>
        </rulesets>
        <failOnViolation>false</failOnViolation>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>check</goal>
                <goal>cpd-check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

- `rulesets` 指向自定义规则集文件
- `failOnViolation=false` 防止因违规导致构建失败
- `executions` 中包含 `check`（PMD 检查）和 `cpd-check`（重复代码检查）

#### 2.3 添加 reporting 插件

在 `<reporting><plugins>` 中添加 `maven-pmd-plugin`，使 `mvn site` 生成 PMD/CPD 报告页面：

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pmd-plugin</artifactId>
    <version>${org.apache.maven.plugins.maven-pmd-plugin.version}</version>
    <configuration>
        <rulesets>
            <ruleset>pmd-rules.xml</ruleset>
        </rulesets>
    </configuration>
</plugin>
```

### 步骤 3：验证

1. 运行 `mvn clean -DskipTests install` → **BUILD SUCCESS**，PMD 在 verify 阶段正常执行
2. 运行 `mvn site` → **BUILD SUCCESS**，日志显示生成了 `"PMD" report` 和 `"CPD" report`
3. 打开 `target/site/index.html`，左侧菜单出现 **Project Reports > PMD / CPD**
4. `docs-core/target/site/pmd.html` 中检测到 42 个违规（包括 CyclomaticComplexity、CouplingBetweenObjects 等）

## 修改的文件清单

| 文件 | 操作 | 说明 |
|------|------|------|
| `pom.xml` | 修改 | 添加 maven-pmd-plugin 版本、build 配置、reporting 配置 |
| `pmd-rules.xml` | 新建 | 自定义 PMD 规则集（4 条规则覆盖 5 个指标） |
