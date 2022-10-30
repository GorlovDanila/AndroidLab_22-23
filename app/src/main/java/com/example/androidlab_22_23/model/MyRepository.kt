package com.example.androidlab_22_23.model

object MyRepository {

    var itemList: List<MyItem> = listOf()

    private val characters: List<MyItem> = listOf(
        MyItem.Character(
            id = 0,
            title = "Танджиро Камадо",
            affiliation = "Истребитель демонов",
            cover = "https://static.wikia.nocookie.net/kimetsu-no-yaiba/images/e/e1/Tanjiro_anime.png/revision/latest?cb=20181128204204",
        ),
        MyItem.Character(
            id = 1,
            title = "Зеницу Агацума",
            affiliation = "Истребитель демонов",
            cover = "https://static.wikia.nocookie.net/kimetsunoyaiba/images/e/e0/Zenitsu_anime_design.png/revision/latest?cb=20190523134359&path-prefix=ru",
        ),
        MyItem.Character(
            id = 2,
            title = "Иноске Хашибира",
            affiliation = "Истребитель демонов",
            cover = "https://static.wikia.nocookie.net/kimetsunoyaiba/images/9/94/Inosuke_anime_design.png/revision/latest?cb=20190526144228&path-prefix=ru",
        ),
        MyItem.Character(
            id = 3,
            title = "Канао Цуюри",
            affiliation = "Истребитель демонов",
            cover = "https://static.wikia.nocookie.net/kimetsu-no-yaiba/images/c/cd/Kanao_anime.png/revision/latest?cb=20191028091138",
        ),
        MyItem.Character(
            id = 4,
            title = "Генья Шинадзугава",
            affiliation = "Истребитель демонов",
            cover = "https://static.wikia.nocookie.net/kimetsu-no-yaiba/images/b/b9/Genya_Anime.png/revision/latest?cb=20191220221437",
        ),
        MyItem.Character(
            id = 5,
            title = "Мудзан Кибуцуджи",
            affiliation = "Демоны двенадцати лун (Лидер)",
            cover = "https://static.wikia.nocookie.net/kimetsu-no-yaiba/images/0/0e/Muzan_Kibutsuji_Full_Body_%28Anime%29.png/revision/latest?cb=20210731042132",
        ),
        MyItem.Character(
            id = 6,
            title = "Кокушибо",
            affiliation = "Первая Высшая Луна",
            cover = "https://static.wikia.nocookie.net/kimetsu-no-yaiba/images/f/f3/Kokushibo_colored_body.png/revision/latest?cb=20200413041714",
        ),
        MyItem.Character(
            id = 7,
            title = "Доума",
            affiliation = "Вторая высшая луна",
            cover = "https://static.wikia.nocookie.net/kimetsunoyaiba/images/3/34/Doma_Anime.png/revision/latest?cb=20220324172927&path-prefix=ru",
        ),
        MyItem.Character(
            id = 8,
            title = "Аказа",
            affiliation = "Третья высшая луна",
            cover = "https://static.wikia.nocookie.net/kimetsu-no-yaiba/images/e/e1/Akaza_Full_Body_Design_%28Anime%29.png/revision/latest?cb=20211013090054",
        ),
        MyItem.Character(
            id = 9,
            title = "Незуко Камадо",
            affiliation = "Не определено",
            cover = "https://static.wikia.nocookie.net/kimetsunoyaiba/images/2/2f/Nezuko_anime_design.png/revision/latest?cb=20190522101557&path-prefix=ru",
        )
    )

    private val advertisements: List<MyItem> = listOf(
        MyItem.Advertisement(
            id = 0,
            title = "Raid: Shadow Legends",
            img = "https://img10.reactor.cc/pics/post/full/CZ-75-%28Girls-Frontline%29-Girls-Frontline-Anime-the-sourkraut-5714690.jpeg"
        ),
        MyItem.Advertisement(
            id = 1,
            title = "Dr Pepper",
            img = "https://cs14.pikabu.ru/post_img/big/2021/07/12/9/162610362513239001.png"
        ),
        MyItem.Advertisement(
            id = 2,
            title = "The White Black Monster",
            img = "https://www.meme-arsenal.com/memes/04d380ae13d80b60d857fac75de71f97.jpg"
        )
    )

    fun generateList(size: Int) {
        val list = mutableListOf<MyItem>()
        for (i in 0 until size) {
            if (i % 6 == 0 && i != 0) {
                val item =
                    advertisements[(advertisements.indices).random()] as MyItem.Advertisement
                val newItem = item.copy()
                list.add(newItem)
            } else {
                val item =
                    (characters[(characters.indices).random()] as MyItem.Character)
                val newItem = item.copy()
                newItem.title = newItem.title
                list.add(newItem)
            }
        }
        itemList = list

    }

    fun addItem(position: Int, item: MyItem.Character) {
        val list = itemList.toMutableList()
        if (position >= itemList.size - 1) {
            list.add(item)
        } else {
            list.add(position, item)
            for (i in position until itemList.size) {
                if (list[i] is MyItem.Advertisement && i % 6 != 0) {
                    list[i] = list[i - 1].also {
                        list[i - 1] = list[i]
                    }
                }
            }
        }
        itemList = list.toList()
    }

    fun deleteItem(position: Int) {
        val list = itemList.toMutableList()
        list.removeAt(position)
        for (i in position until itemList.size - 2) {
            if (list[i] is MyItem.Advertisement && i % 6 != 0) {
                /*
                val adItem = list[i] as MyModel.Advertisement
                val genItem = list[i+1] as MyModel.Item
                list[i] = genItem
                list[i+1] = adItem
                */
                list[i] = list[i + 1].also {
                    list[i + 1] = list[i]
                }
            }
            if (list[list.size - 1] is MyItem.Advertisement) {
                list.removeAt(list.size - 1)
            }
        }
        itemList = list.toList()
    }
}