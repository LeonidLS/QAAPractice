# QAAPractice

#### Общее описание и порядок действий #### 
- markdown format 
```
 *.md - Markdown format, язык разметки для форматирования прстого текста. 
 Для удобного восприятия текста и разделов, Intellij Idea предлагает специальный Markdown plugin от JetBrains.
```
- плагины и зависимости используемые для тестирования и отчетности 
```
- Тестирование: 

TestNG:  

        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>6.14.3</version>
        </dependency>


Surefire plugin:

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

Плагин: AspectJ - применяется для расширения возможностей аннотаций, 
а также это возможность добавить на этапе компиляции или рантайма в классы 
некую функциональность, которой раньше там не было, или изменить существующую.

        <properties>
            <aspectj.version>1.8.10</aspectj.version>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        </properties>
	
	
        <dependencies>
            <dependency>
                <groupId>org.aspectj</groupId>
                <artifactId>aspectjweaver</artifactId>
                <version>${aspectj.version}</version>
            </dependency>
        </dependencies>


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

- что должно быть установлено "на борту"
```
TestNG, Surefire, AspectJ - https://mvnrepository.com/
Allure-Framework: https://github.com/allure-framework/
Maven: https://maven.apache.org/
```

- запуск тестов 
```
1. Запуск тестов может осуществляться из командной строки при помощи стека команд: mvn clean install.
2. Из IDE Idea, запуск, в данном случае, может начинаться с запуска на исполнение testng.xml.  
```

### Использование плагина Allure ###

#### Модификация pom.xml ####

 - Добавление Dependency для подключения Allure 
```
 <dependency>
        <groupId>io.qameta.allure</groupId>
         <artifactId>allure-testng</artifactId>
          <version>2.12.0</version>
  </dependency> 
```
#### Модификация переменных среды #### 
```
После загрузки и распаковки Allure, необходимо прописать путь к директории bin. 
Переменные среды -> Системные переменные -> path: <path to folder>\ALLURE\allure-commandline-2.13.0\allure-2.13.0\bin
```

#### Использование аннотаций Allure ####
```
1) @Step - шаг теста, атомарное действие из которых может строится тест. Используется для того, чтобы в итоговом отчете, результаты теста были декомпозированы по шагам, когда каждый шаг может обладать собственным ассертом.

@Step
public static void methodName(){}

2) @DisplayName - позволяет добавить дружественное для человеческого восприятия описание теста.

@Test
@DisplayName("Checking the correctness of the user-validation form")
public static void methodName(){}

3) @Severity - используется для приоритезации тестов, исходя из "важности" покрываемого ими контента.

@Test
@Severity(SeverityLevel.CRITICAL)
public static void methodName(){}

4) @Story - определяет принадлежность теста, к покрытию определенной user-story, в контексте bdd.

@Test
@Story("Base support for bdd annotations")
public static void methodName(){}
```

#### Генерация отчетов Allure ####
```
1) mvn clean test - для запуска жизненного цикла сборки Maven, до фазы test - включительно; 
Результатом на этом шаге будет создание директории allure-results, c файлами отчетов формата json.

2) Находясь в директории проекта (в которой уже находится папка allure-results) из командной строки 
выполнить команды: allure generate  //будет сгенерированы отчеты в формате веб-страниц;
либо allure serve //произойдет запуск stadnalone сервера Allure, c последующим автоматическим вызовом 
броузера, для отображения сгенерированных отчетов в формате веб-страниц; 
```

### Запуск тестов по группам ###
- TestNG.xml 

```
1) В корневом каталоге проекта должен быть создать TestNG.xml,
со следующим содержанием: 

<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="JIRA Suite" parallel="false">
    <listeners>
        <listener class-name="utils.TestListener"/>
    </listeners>
    <test name="Issue">
        <groups>
            <run>
                <exclude name="Group1"/>
                <include name="Group2"/>
           </run>
        </groups>
        <classes>
            <class name="JIRATest"/>
        </classes>
    </test>
</suite>

Соответственно группа указанная в тэге <include> будет выполнятся, а группа указанная в тэге <exclude> выполняться не будет. 
```
- настройка IDE 
```
2) Указываем путь к TestNG.xml, чтобы можно было запускать проект "через" сам xml-файл:
Intellij Idea -> Run/Debug Configuration -> TestNG -> Test kind: Suite, Suite: <path_to_testng.xml>
```

- Использование аннотаций 
```
3) @Test(groups = {"Group1"}) / @Test(groups = {"Group1", "Group2"}) тест может одновременно принадлежать как к одной так и к нескольким группам. 
Note: В случае, если тест одновременно принадлежит к двум группам, одна из которых добавлена в include, а другая указана в exclude - тест выполняться не будет.
```
- Запуск тестов 
```
Праквой кнопкой мышки на файле TestNG.xml, Run <path_to_file>/TestNG.xml
```
