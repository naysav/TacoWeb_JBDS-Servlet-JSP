# TacoWeb_JBDS-Servlet-JSP
# http://87.247.157.161:8080/TacoBoom/ - приложение, развернутое на сервере.
Я использовал 3 БД: product, tacos, user_account.

Содержание БД product, с которым работает моя программа:

| CODE          | NAME                           | PRICE |
| ------------- |:-----------------------------: | -----:|
| P001     | Свежеиспеченная кукурузная тортилья | 23    |
| P002     | курица с хрустящей корочкой         | 41    |
| P003     | ароматный чеснок                    | 12    |
| P004     | мелко пассерованный лук             | 14    |
| P005     | томаты                              | 27    |
| P006     | ростки фасоли МАШ                   | 21    |
| P007     | сыр творожный                       | 11    |
| P008     | авокадо                             | 23    |

```sql
create table product
(
    CODE  varchar(20)  not null
        primary key,
    NAME  varchar(128) not null,
    PRICE float        not null
);
```

```sql
create table tacos
(
    chicken     varchar(60) null,
    garlic      varchar(60) null,
    onion       varchar(60) null,
    tomato      varchar(60) null,
    haricot     varchar(60) null,
    cheese      varchar(60) null,
    avocado     varchar(60) null,
    total_price float       null,
    id          int         not null,
    flapjack    varchar(60) null,
    user        varchar(40) null
);
```
```sql
create table user_account
(
    USER_NAME varchar(30) not null
        primary key,
    GENDER    varchar(1)  not null,
    PASSWORD  varchar(30) not null
);
```
