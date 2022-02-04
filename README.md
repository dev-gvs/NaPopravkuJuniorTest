# NaPopravkuJuniorTest

Тестовое задание на вакансию Junior Android Developer от НаПоправку.


## Экраны

| [Список репозиториев](app/src/main/java/kz/gvsx/napopravkujuniortest/ui/main) | [Детальная информация репозитория](https://github.com/dev-gvs/NaPopravkuJuniorTest/tree/master/app/src/main/java/kz/gvsx/napopravkujuniortest/ui/details) |
|-----------|---------|
| <video src='https://user-images.githubusercontent.com/44653764/152516994-7f62f6f3-a7dd-4267-ac59-fa76a1393dd7.mp4' width=360/> | <video src='https://user-images.githubusercontent.com/44653764/152518807-48325055-68f6-4831-b3fe-793e3c953b46.mp4' width=360/> |


## Проблемы, с которыми столкнулся

* Отображение `LoadStateAdapter` при первоначальной загрузке — по-умолчанию, при добавлениее данного адаптера в `header` и/или `footer`, он отображается только при 
`LoadType.PREPEND` и `LoadType.APPEND` соответственно. [Решение](app/src/main/java/kz/gvsx/napopravkujuniortest/Utils.kt#L37-L54) — функция для создания `ConcatAdapter`, 
где в `header` адаптер передается `LoadType.REFRESH`.


## Технологии

* [100% Kotlin](https://kotlinlang.org/)
  * [Kotlin serialization](https://github.com/Kotlin/kotlinx.serialization) — плагин для JSON сериализации в `data class`
  * [Android KTX](https://developer.android.com/kotlin/ktx) — Kotlin расширения для Jetpack и других Android библиотек
* [Retrofit](https://github.com/square/retrofit) — type-safe HTTP-клиент для доступа к API
  * [Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) — перехват запросов в сеть для логгирования
  * [Kotlin Serialization Converter](https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter) — `Converter.Factory` для Kotlin serialization
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) — внедрение зависимостей
* [Современная архитектура](https://developer.android.com/jetpack/guide)
    * Single activity architecture + MVVM
    * [Архитектурные компоненты Android](https://developer.android.com/topic/libraries/architecture)
      * [View Binding](https://developer.android.com/topic/libraries/view-binding) — упрощенный доступ к View
      * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) — хранения и управления данными, связанными с пользовательским интерфейсом, с учетом жизненного цикла
      * [Coroutines](https://developer.android.com/topic/libraries/architecture/coroutines) — выполнение действий при изменении состояния жизненного цикла(`viewModelScope`, `repeatOnLifecycle`)
      * [Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) — пагинация для загрузки данных
* UI
  * [Material Design](https://material.io/design)
  * [ConstraintLayout](https://developer.android.com/training/constraint-layout) — создание сложных интерфейсов с плоской иерархией
  * [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview) — эффективное отображение больших наборов данных в виде списка
  * [Fragments](https://developer.android.com/guide/fragments) — отображение экранов
  * [Fragment manager](https://developer.android.com/guide/fragments/fragmentmanager) — навигация между фрагментами
* [Coil](https://coil-kt.github.io/coil/) — библиотека для загрузки изображений
* [ViewBindingPropertyDelegate](https://github.com/androidbroadcast/ViewBindingPropertyDelegate) — более удобная работа с ViewBinding
* [Timber](https://github.com/JakeWharton/timber) — логгер, который предоставляет утилиты поверх обычного класса Log в Android
* [LeakCanary](https://github.com/square/leakcanary/) — обнаружение утечек памяти
