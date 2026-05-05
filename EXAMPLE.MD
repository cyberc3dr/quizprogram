# Spring. Примеры

## Способы конфигурации Spring контекста.

1. XML

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
```

```xml
<beans>
    <bean id="myBean" class="com.example.MyClass">
        <property name="myProperty" value="someValue"/>
    </bean>
</beans>
```

2. XML + аннотации

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
```

```xml
<beans>
    <context:component-scan base-package="com.example"/>
</beans>
```

3. Java-based конфигурация

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
    @Bean
    public MyClass myBean() {
        MyClass myBean = new MyClass();
        myBean.setMyProperty("someValue");
        return myBean;
    }
}
```

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
```

## Создание бина через XML используя конструктор с параметрами

```xml
    <bean id="firstStudent" class="spring3_ioc.xml.bean.Student">
        <!-- Инициализация через конструктор (используется только конструктор со всеми параметрами!)
             Аргументы указываются в теге constructor-arg в формате ключ значение -->
        <constructor-arg name="name" value="Vasya"/>
        <constructor-arg name="age" value="20"/>
        <constructor-arg name="group" value="22-IVT-4"/>
        <constructor-arg name="creationType" value="Init by constructor"/>
    </bean>
```

## Внутренний и внешний factory методы

1. Внутренний:

```xml
    <bean id="thirdStudent" class="spring3_ioc.xml.bean.Student" factory-method="createStudent">
        <!-- Инициализация через внутренний (определенный в классе Student) фабричный метод.
             В factory-method прописывается название фабричного метода -->
        <constructor-arg name="name" value="Sveta"/>
        <constructor-arg name="age" value="20"/>
        <constructor-arg name="group" value="22-IVT-4"/>
        <constructor-arg name="creationType" value="Init by inner factory method"/>
    </bean>
```

```java
    /**
     * Фабричный метод, используется для инициализации бина через фабричный метод.
     */
    public static Student createStudent(String name, Integer age, String group, String creationType) {
        return new Student(name, age, group, creationType);
    }
```

2. Внешний:

```xml
    <!-- Для инициализации через внешний фабричный метод сначала необходимо инициализировать бин самой фабрики-->
    <bean id="StudentBeanFactory" class="spring3_ioc.xml.bean.StudentBeanFactory"/>
    <bean id="fourthStudent" class="spring3_ioc.xml.bean.Student" factory-method="createStudent" factory-bean="StudentBeanFactory"
          scope="prototype">
        <!-- Инициализация через внешний (определенный в классе StudentBeanFactory) фабричный метод.
             В factory-method прописывается название фабричного метода.
             В factory-bean="StudentBeanFactory" указывается id бина фабрики -->
        <constructor-arg name="name" value="Anna"/>
        <constructor-arg name="age" value="24"/>
        <constructor-arg name="group" value="22-IVT-3"/>
        <constructor-arg name="creationType" value="Init by external factory method"/>
    </bean>
```

```java
/**
 * Фабрика студентов
 */
public class StudentBeanFactory {
    /**
     * Фабричный метод создания студента
     */
    public static Student createStudent(String name, Integer age, String group, String creationType) {
        return new Student(name, age, group, creationType);
    }
}
```

## XML конфигурация с использованием аннотаций

```xml
<beans>
    <context:component-scan base-package="spring3_ioc.xml.bean"/>
</beans>
```

```java
@Component
public class Student {
    private String name;
    private Integer age;
    private String group;
    private String creationType;
    // Конструктор, геттеры и сеттеры
}
```

## DI через XML
Внедрение через конструктор:
```xml
    <bean class="spring4_di.xml.bean.DifficultBean">
        <constructor-arg name="exampleBean" ref="anotherBean"/>
        <constructor-arg name="text" value="Bean with Dependency Injection"/>
        <constructor-arg name="number" value="1000"/>
    </bean>
```

Здесь ref - название бина который будет внедрен.

Внедрение через сеттер:
```xml
    <!-- Бин, в который происходит внедрение зависимости через сеттер.
         Внедрение происходит при помощи <property ref="..."/>,
         где ref указывает на id бина, который будет внедрен.
         В отличие от constructor-arg, здесь используется setter-метод. -->
    <bean class="spring4_di.xml.bean.FlexibleBean">
        <property name="exampleBean" ref="exampleBean"/>
        <property name="text" value="Bean with Setter DI"/>
    </bean>
```

## Изменение скопа Bean в Spring

Annotation:
```java
@Component
@Scope("prototype")
public class Service { }
```

XML:
```xml
    <bean id="myBean" class="com.example.MyClass" scope="prototype"/>
```

## Использование prototype бинов в singleton бинах

```java
@Component
public class SingletonBean {
    
    @Lookup
    public PrototypeBean getPrototypeBean() {
        // Этот метод будет переопределен Spring'ом для возвращения нового экземпляра PrototypeBean при каждом вызове
        return null;
    }
}
```

```java
@Component
public class SingletonBean {
    
    @Autowired
    private ObjectFactory<PrototypeBean> prototypeBeanFactory;
    
    public PrototypeBean getPrototypeBean() {
        // Получаем новый экземпляр PrototypeBean при каждом вызове
        return prototypeBeanFactory.getObject();
    }
}
```