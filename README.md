# Steps
## Шагомер для подсчетов шагов и сожженых калорий на каждый день

## 🧐 Реализованные фичи:
- ** Ежедневный подсчет пройденных шагов, калорий, пройденных километров, сэкономленного углекислого газа и собранных деревьев (когда клиент достигает поставленной цели по шагам, он собирает одно дерево)
- ** Отчет ежедневный и за все время (с еженедельным графиком пройденнных шагов)
- ** Редактирования настроек (цели по шагам, изменение своего веса, длины шага и других параметров, которые влияют на подсчет каллорий), а также изменение темы 

## Стек технологий:
- Kotlin
- UI полностью на Jetpack Compose
- Single Activity Application паттерн
- MVVM в презентационном слое (ViewModel + StateFlow)
- Dagger Hilt и многомодульность
- Kotlin Coroutines для асинхронных операций + Flow
- Room, SharedPreferences и DataStore для БД
- Unit, Ui - тесты
- Собственные GradlePlugin (модуль buildLogic)

## Тестирование
 - **Unit тесты**:
 - - com.example.localdata.data.source.dao.DaysDaoTest
   - com.example.localdata.domain.usecase.IncrementStepCountUseCaseImplTest
   - com.example.localdata.domain.usecase.UpdateDaySettingsUseCaseImplTest
   - com.example.localdata.domain.usecase.GetDayUseCaseImplTest
   - com.example.localdata.data.repository.ThemeRepositoryImplTest
- **UI тесты**:
- - com.example.profile.MoreSettingsScreenTest - тест компоузного экрана с отображением диалогов

## Анимация
 - com.example.glonav.StepsNavHost - переходы между табами
 - com.example.designsystem.DSBarChart - анимация отрисовки графика пройденных шагов
   
## Многомодульность (9 модулей)

