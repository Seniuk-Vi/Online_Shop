# Online_Shop
Technologies: Java, Servlets, MYSQL

During training, I created a Servlets web project with MVC structure:
* Connected MySQL DB with JDBC API, also added connection pool to it
* Implemented Internationalization (en, uk)
* Used Command, Singleton Design Pattern
* Used Apache Tomcat as a servlet container
* Worked with JSP, used tags from JSTL and created my own tags (custom tag library, tag file)
* Implemented PRG
* Userd filters, sessions, listeners
* Authentication and authorization, delineation of access rights of system users to program components are implemented
* Used log4j logging utility
* Added validation to all field and covered some code with tests
* Implemented pagination
* Added custom exceptions to pass normal error messages to client

Інтернет магазин

Магазин має каталог Товарів, для якого необхідно реалізувати можливість:
- сортування за назвою товару (az, za);
- сортування товарів за ціною (від дешевих до дорогих, від дорогих до дешевих);
- сортування товарів за новизною;
- вибірки товарів за параметрами (категорія, проміжок ціни, колір, розмір, тощо).
Користувач переглядає каталог і може додавати товари до свого кошика. Після додавання товарів у кошик, зареєстрований користувач може зробити Замовлення. Для незареєстрованого користувача ця опція недоступна. Після розміщення замовлення, йому (замовленню) присвоюється статус 'зареєстрований'.
Користувач має особистий кабінет, в якому може переглянути свої замовлення.
Адміністратор системи володіє правами:
- додавання/видалення товарів, зміни інформації про товар;
- блокування/розблокування користувача;
- переведення замовлення зі статусу 'зареєстрований' до 'оплачений' або 'скасований'.
