## Вступ
Вітаю вас! 
Це проект **Mace_Engine**, метою якого є створення на мові програмування Java із використанням LWJGL (OpenGL) власного falling-sand симулятора. Для максимальної зручності було обрано JDK 17, адже цей SDK є LTS версією. Середовище розробки - IntelliJ IDEA Ultimate.

##### Приклади ігор аналогічного жанру
![Noita](https://user-images.githubusercontent.com/113986988/225435438-28f2d5bb-22aa-43a8-af1e-3a643ecfbf26.png)
>*Гра "Noita", в основі якої лежить falling-sand симулятор.*

![The Sandbox](https://user-images.githubusercontent.com/113986988/225433957-ba371ab3-9c79-47bb-b59e-0406fc95ca99.png)
>*Мобільна гра "The Sandbox", котра теж є одним із прикладів цікавих falling-sand симуляторів.*

## Пролог
Причиною створення проекту було завдання від викладача під час навчання в університеті. Спочатку ми вагалися, щодо обрання конкретного жанру, але врешті-решт було вирішено створення гри саме з фізичною моделлю кожного умовного "пікселю". Такий жанр у рамках розробки не є надто складним, а також створення першої працючої альфа-версії суттєво б спрощувало подальше доповнення контентом, адже усі необхідні основні механіки були б уже реалізованими і відповідно залишалося б доповнювати новими матеріалами з їх фізикою та особливостями. Обрання графічного фреймворку - теж було горячою дискусією, хоча далеко не усі виступали з певною позицією та здебільшого залишалися осторонь. Врешті було обрано LWJGL, завдяки певним перевагам у створенні продукту такого роду.

## Roadmap
- [x] Створення вікна для виводу графічної інформації.
- [x] Створення матриці із даними необхідними для візуалізації кожного пікселя.
- [x] Виведення даних з матриці (Шейдери GLSL).
- [x] Реалізація взаємодії гравця із симуляцією.
	- [x] Використовуючи клавіатуру.
	- [x] Використовуючи комп'ютерну миш.
- [ ] Створення заставки та інших ймовірних ігрових сцен.
- [ ] Можливість збереження результатів симуляції та їх відтворення.
- [ ] Перехід на більш ефективне графічне виведення інформації.
	- [ ] Використання інших систем зображення графічного інтерфейсу.
	- [ ] Покращення загального візуального виду. Введення різномаїття кольорів та певних паттернів для матеріалів.
- [ ] Створення інерціальної системи.
	- [ ] Для рідин.
	- [ ] Для газів.
	- [ ] Для твердих речовин.
- [ ] Загальна оптимізація процесів, зменшення необхідних ресурсів ПК.
- [ ] Реалізувати єдиний файловий пакет.
