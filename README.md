# Архитектура

Требуется подумать над архитектурой учебного проекта, а именно подумать, какие варианты использования могут быть у этого приложения, какие действия и в какой последовательности будут выполняться в каждом из вариантов использования, как эти действия могут быть отражены в объектной модели нашего приложения.

Для лучшего понимания проектирования рекомендую ознакомиться с кратким обзором [паттернов проектирования](http://habrahabr.ru/post/210288/) и [книгой](http://www.books.ru/books/priemy-obektno-orientirovannogo-proektirovaniya-patterny-proektirovaniya-8451/?show=1) для более детального изучения.

# Git

Кроме того, необходимо ознакомиться с этими двумя статьями: [статья 1](http://habrahabr.ru/post/123111/) и [статья 2](http://dev-lab.info/2013/08/%D1%88%D0%BF%D0%B0%D1%80%D0%B3%D0%B0%D0%BB%D0%BA%D0%B0-%D0%BF%D0%BE-git-%D0%BE%D1%81%D0%BD%D0%BE%D0%B2%D0%BD%D1%8B%D0%B5-%D0%BA%D0%BE%D0%BC%D0%B0%D0%BD%D0%B4%D1%8B-%D1%81%D0%BB%D0%B8%D1%8F%D0%BD/), а также с [интерактивной обучалкой по git](http://habrahabr.ru/post/169743/), и сделать отдельную ветку от текущего проекта в своем форке, а затем слить сделанные в ветке изменения с основным бранчем.

По шагам это будет выглядеть следующим образом:

1. Переходим по адресу нашего проекта [тут](https://github.com/rgordeev/phonebook)

2. Копируем ссылку из поля HTTPS clone URL

3. Открываем окно терминала и в директории, где хранятся наши проекты, выполняем команду 
 `git clone https://github.com/rgordeev/phonebook.git`  

4. Затем опять переходим на страницу проекта на [github](https://github.com/rgordeev/phonebook) и делаем форк, нажав на кнопку в правом верхнем углу (для этого требуется регистрация на github)

5. Переходим в терминале в директорию в которую мы склонировали проект и выполняем в нем команду
`git remote add <произвольный алиас, например myrepo> <url адрес форка, расположенного уже на своей странице>`

6. Выполняем команду `git remote -v` чтобы убедиться, что все было корректно добавлено. Должны будем увидеть нечто, похожее на
`origin  https://github.com/rgordeev/phonebook.git
<указанный алиас, например myrepo>  git://github.com/<имя выбранное Вами при регистрации на github>/phonebook.git`

Для более детального ознакомления с git советую почитать [книгу](http://git-scm.com/book/ru) 

Соответственно задание по git заключается в 

1. Создании дополнительной ветки в клонированном репозитории

2. Внесении произвольных изменений в код проекта

3. Коммите этих изменений в ветку

4. Слиянии ветки с основным бранчем master.

# Gradle

В задании по Gradle требуется в текущий проект добавить зависимость от библиотеки [Apache commons lang](http://commons.apache.org/proper/commons-lang/) и реализовать медот equals класса Person, используя статический медот StringUtils.equals(str1, str2) класса StringUtils из добавленной библиотеки.
