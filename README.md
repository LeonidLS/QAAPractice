# QAAPractice

#### Общее описание и порядок действий #### 
- markdown format 
```
 *.md - Markdown format, язык разметки для форматирования прстого текста. 
 Для удобного восприятия текста и разделов, Intellij Idea предлагает специальный Markdown plugin от JetBrains.
```
- плагины и зависимости используемые для тестирования и отчетности 
```
Тестирование: TestNG, Surefire.

Плагин: AspectJ - применяется для расширения возможностей аннотаций, 
а также это возможность добавить на этапе компиляции или рантайма в классы 
некую функциональность, которой раньше там не было, или изменить существующую.

Отчеты: Allure.

Сборщик: Maven. 
```

### Порядок действий ### 
- pom.xml 
```
Добавляем необходимые плагины и зависимости.
```
- testng.xml 
```
Добавляем testng.xml в корень проекта. 
Intellij Idea -> Run/Debug Configuration -> TestNG -> Test kind: Suite, Suite: <path_to_testng.xml>
Указываем порядок запуска определенных групп тестов.
```
- аннотации 
```
TestNG - использует свой язык аннотаций, для определения в коде тестов, порядка их выполнения, пред- и пост-условий,
Allure - также имеет свой язык аннотаций, позволяющий детализировать итоговые отчеты от прогона тестов, добавляя деление 
на фичи, тестовые шаги и т.д.
```

#### Алгоритм для подключения Allure

##### 1. обновить pom.xml
- добавить dependency
```
  <dependency>
        <groupId>io.qameta.allure</groupId>
         <artifactId>allure-testng</artifactId>
          <version>2.12.0</version>
  </dependency>
```
- добавить plugins		
```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.20</version>
            <configuration>
                <suiteXmlFiles>
                    <suiteXmlFile>TestNG.xml</suiteXmlFile>
                </suiteXmlFiles>
                <argLine>
                    -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar"
                </argLine>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>org.aspectj</groupId>
                    <artifactId>aspectjweaver</artifactId>
                    <version>${aspectj.version}</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```
- добавить properties
```
 <properties>
        <aspectj.version>1.8.10</aspectj.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
```
- добавить TestNG.xml в корень проекта (что бы явно указать какие тесты и как запускать, не параллельно)
```
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Parallel test suite" >
    <test name="Issue">
        <classes>
            <class name="JIRATest"/>
        </classes>
    </test>
</suite>
```
- обновить тест - добавить @Feature
##### 2. установить Allure CLI
```
https://docs.qameta.io/allure/#_installing_a_commandline
```
##### 3. запустить тесты 
```mvn clean test```
##### 4. выполнить в корне проекта консольную команду и получить отчет
    ```allure generate```
Отчет будет в корне проекта в папке "allure-report" 

В помощь - `https://www.swtestacademy.com/allure-testng/`

#### Как добавить группу @smoke, @regression?
##### 1. определить группы в TestNG.xml
```
<groups>
    <run>
        <include name="Regression"/>
        <exclude name="SKIP"/>
    </run>
</groups>
```
##### 2. добавить группу в тест
```
@Test(groups = {"Regression", "SKIP"})
```
##### 3. добавить группу в @BeforeTest @AfterTest
```
@BeforeTest(groups = "Regression")
```
