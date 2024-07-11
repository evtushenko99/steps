# Steps
## Шагомер для подсчетов шагов и сожженых калорий на каждый день

## 🧐 Реализованные фичи:
- Ежедневный подсчет пройденных шагов, калорий, пройденных километров, сэкономленного углекислого газа и собранных деревьев (когда клиент достигает поставленной цели по шагам, он собирает одно дерево)
- Отчет ежедневный и за все время (с еженедельным графиком пройденнных шагов)
- Редактирования настроек (цели по шагам, изменение своего веса, длины шага и других параметров, которые влияют на подсчет каллорий), а также изменение темы 

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
   - com.example.localdata.data.source.dao.DaysDaoTest
   - com.example.localdata.domain.usecase.IncrementStepCountUseCaseImplTest
   - com.example.localdata.domain.usecase.UpdateDaySettingsUseCaseImplTest
   - com.example.localdata.domain.usecase.GetDayUseCaseImplTest
   - com.example.localdata.data.repository.ThemeRepositoryImplTest
- **UI тесты**:
   - com.example.profile.MoreSettingsScreenTest - тест компоузного экрана с отображением диалогов

## Анимация
 - com.example.glonav.StepsNavHost - переходы между табами
 - com.example.designsystem.DSBarChart - анимация отрисовки графика пройденных шагов

## Хранение данных 
Модуль localData

## Как это выглядит
<img src="https://github.com/evtushenko99/steps/assets/46201617/1791aba3-0ed2-4414-8fa9-1734a93356d0" width="200" />
![Steps Отчет за сегодня](https://github.com/evtushenko99/steps/assets/46201617/6890a3a2-6ce9-4729-a900-8d889314befc)
![Отчет сводка](https://github.com/evtushenko99/steps/assets/46201617/abf6cf6c-69e1-42bb-973c-0237f6e7eead)
![Steps Еще без иконки](https://github.com/evtushenko99/steps/assets/46201617/93655114-1468-407a-8ba7-71bce0485b05)
![Steps Еще](https://github.com/evtushenko99/steps/assets/46201617/0013f78e-fb97-4c64-9df5-8a1744d3ebf4)

## Многомодульность (10 модулей)

