package com.example.planify.main.features.meeting.data.repositories_impl

import com.example.planify.main.features.meeting.domain.repositories.MeetingRepository
import com.example.planify.main.features.meeting.entities.Invite
import com.example.planify.main.features.meeting.entities.Meeting
import com.example.planify.main.features.meeting.entities.MeetingInfo
import com.example.planify.main.features.profile.Profile
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

object HardcodedWeekData {

    // Предопределенные пользователи
    private val alex = Profile(
        userId = 1L,
        firstName = "Александр",
        lastName = "Иванов",
        position = "Team Lead",
        department = "Разработка",
        profileImageUrl = "https://example.com/avatars/1.jpg"
    )

    private val maria = Profile(
        userId = 2L,
        firstName = "Мария",
        lastName = "Петрова",
        position = "Frontend Developer",
        department = "Разработка",
        profileImageUrl = "https://example.com/avatars/2.jpg"
    )

    private val ivan = Profile(
        userId = 3L,
        firstName = "Иван",
        lastName = "Сидоров",
        position = "Backend Developer",
        department = "Разработка",
        profileImageUrl = "https://example.com/avatars/3.jpg"
    )

    private val anna = Profile(
        userId = 4L,
        firstName = "Анна",
        lastName = "Смирнова",
        position = "Product Manager",
        department = "Менеджмент",
        profileImageUrl = "https://example.com/avatars/4.jpg"
    )

    private val dmitry = Profile(
        userId = 5L,
        firstName = "Дмитрий",
        lastName = "Кузнецов",
        position = "UI/UX Designer",
        department = "Дизайн",
        profileImageUrl = "https://example.com/avatars/5.jpg"
    )

    private val sofia = Profile(
        userId = 6L,
        firstName = "София",
        lastName = "Попова",
        position = "QA Engineer",
        department = "Тестирование",
        profileImageUrl = "https://example.com/avatars/6.jpg"
    )

    private val mikhail = Profile(
        userId = 7L,
        firstName = "Михаил",
        lastName = "Васильев",
        position = "DevOps Engineer",
        department = "Инфраструктура",
        profileImageUrl = "https://example.com/avatars/7.jpg"
    )

    private val ekaterina = Profile(
        userId = 8L,
        firstName = "Екатерина",
        lastName = "Павлова",
        position = "Data Analyst",
        department = "Аналитика",
        profileImageUrl = "https://example.com/avatars/8.jpg"
    )

    // Генерация данных на неделю (с сегодняшнего дня)
    fun getWeekMeetings(): HashMap<LocalDate, List<MeetingInfo>> {
        val meetingsMap = HashMap<LocalDate, List<MeetingInfo>>()

        val today = LocalDate.now()

        // Понедельник
        val monday = today.with(java.time.DayOfWeek.MONDAY)
        meetingsMap[monday] = listOf(
            MeetingInfo(
                meeting = Meeting(
                    title = "Планирование недели",
                    description = "Совещание по планированию задач на неделю",
                    timeStart = LocalDateTime.of(monday, LocalTime.of(10, 0)),
                    duration = Duration.ofHours(2),
                    location = "Конференц-зал A"
                ),
                invites = listOf(
                    Invite(alex.userId, true),
                    Invite(maria.userId, true),
                    Invite(ivan.userId, true),
                    Invite(anna.userId, true)
                ),
                participants = listOf(alex, maria, ivan, anna)
            ),
            MeetingInfo(
                meeting = Meeting(
                    title = "Дизайн-ревью",
                    description = "Обсуждение новых макетов интерфейса",
                    timeStart = LocalDateTime.of(monday, LocalTime.of(15, 0)),
                    duration = Duration.ofHours(2),
                    location = "Переговорная B"
                ),
                invites = listOf(
                    Invite(anna.userId, true),
                    Invite(dmitry.userId, true),
                    Invite(alex.userId, false)
                ),
                participants = listOf(anna, dmitry)
            )
        )

        // Вторник
        val tuesday = monday.plusDays(1)
        meetingsMap[tuesday] = listOf(
            MeetingInfo(
                meeting = Meeting(
                    title = "Ежедневный стендап",
                    description = "Короткое обсуждение текущих задач",
                    timeStart = LocalDateTime.of(tuesday, LocalTime.of(9, 0)),
                    duration = Duration.ofHours(1),
                    location = "Zoom"
                ),
                invites = listOf(
                    Invite(alex.userId, true),
                    Invite(maria.userId, true),
                    Invite(ivan.userId, true),
                    Invite(sofia.userId, true)
                ),
                participants = listOf(alex, maria, ivan, sofia)
            )
        )

        // Среда
        val wednesday = monday.plusDays(2)
        meetingsMap[wednesday] = listOf(
            MeetingInfo(
                meeting = Meeting(
                    title = "Ежедневный стендап",
                    description = "Короткое обсуждение текущих задач",
                    timeStart = LocalDateTime.of(wednesday, LocalTime.of(9, 0)),
                    duration = Duration.ofHours(2),
                    location = "Zoom"
                ),
                invites = listOf(
                    Invite(alex.userId, true),
                    Invite(maria.userId, true),
                    Invite(ivan.userId, true),
                    Invite(sofia.userId, true)
                ),
                participants = listOf(alex, maria, ivan, sofia)
            ),
            MeetingInfo(
                meeting = Meeting(
                    title = "Дизайн-ревью",
                    description = "Обсуждение новых макетов интерфейса",
                    timeStart = LocalDateTime.of(wednesday, LocalTime.of(15, 0)),
                    duration = Duration.ofHours(2),
                    location = "Переговорная B"
                ),
                invites = listOf(
                    Invite(anna.userId, true),
                    Invite(dmitry.userId, true),
                    Invite(alex.userId, false)
                ),
                participants = listOf(anna, dmitry)
            ),
            MeetingInfo(
                meeting = Meeting(
                    title = "Демо продукта",
                    description = "Демонстрация новых функций для команды",
                    timeStart = LocalDateTime.of(wednesday, LocalTime.of(18, 0)),
                    duration = Duration.ofHours(1),
                    location = "Главный зал"
                ),
                invites = listOf(
                    Invite(alex.userId, true),
                    Invite(maria.userId, true),
                    Invite(ivan.userId, true),
                    Invite(anna.userId, true),
                    Invite(dmitry.userId, true),
                    Invite(sofia.userId, true)
                ),
                participants = listOf(alex, maria, ivan, anna, dmitry, sofia)
            ),
            MeetingInfo(
                meeting = Meeting(
                    title = "Код-ревью",
                    description = "Совместный разбор новой функциональности",
                    timeStart = LocalDateTime.of(wednesday, LocalTime.of(20, 0)),
                    duration = Duration.ofHours(2),
                    location = "Переговорная A"
                ),
                invites = listOf(
                    Invite(alex.userId, true),
                    Invite(maria.userId, false),
                    Invite(ivan.userId, true)
                ),
                participants = listOf(alex, ivan)
            )
        )

        // Четверг
        val thursday = monday.plusDays(3)
        meetingsMap[thursday] = listOf(
            MeetingInfo(
                meeting = Meeting(
                    title = "Встреча с клиентом",
                    description = "Обсуждение требований и обратной связи",
                    timeStart = LocalDateTime.of(thursday, LocalTime.of(11, 0)),
                    duration = Duration.ofHours(2),
                    location = "Google Meet"
                ),
                invites = listOf(
                    Invite(anna.userId, true),
                    Invite(dmitry.userId, true),
                    Invite(ekaterina.userId, true)
                ),
                participants = listOf(anna, dmitry, ekaterina)
            )
        )

        // Пятница
        val friday = monday.plusDays(4)
        meetingsMap[friday] = listOf(
            MeetingInfo(
                meeting = Meeting(
                    title = "Ретроспектива спринта",
                    description = "Подведение итогов спринта и обсуждение улучшений",
                    timeStart = LocalDateTime.of(friday, LocalTime.of(15, 0)),
                    duration = Duration.ofHours(3),
                    location = "Конференц-зал A"
                ),
                invites = listOf(
                    Invite(alex.userId, true),
                    Invite(maria.userId, true),
                    Invite(ivan.userId, true),
                    Invite(anna.userId, true),
                    Invite(dmitry.userId, true),
                    Invite(sofia.userId, true),
                    Invite(mikhail.userId, false)
                ),
                participants = listOf(alex, maria, ivan, anna, dmitry, sofia)
            )
        )

        // Суббота
        val saturday = monday.plusDays(5)
        meetingsMap[saturday] = listOf(
            MeetingInfo(
                meeting = Meeting(
                    title = "Мозговой штурм",
                    description = "Генерация идей для нового проекта",
                    timeStart = LocalDateTime.of(saturday, LocalTime.of(12, 0)),
                    duration = Duration.ofHours(2),
                    location = "Креативное пространство"
                ),
                invites = listOf(
                    Invite(dmitry.userId, true),
                    Invite(maria.userId, true),
                    Invite(ekaterina.userId, false)
                ),
                participants = listOf(dmitry, maria)
            )
        )

        // Воскресенье
        val sunday = monday.plusDays(6)
        // Воскресенье без встреч - оставляем пустой список

        return meetingsMap
    }

    // Альтернатива: статическая неделя с конкретными датами
    fun getStaticWeekMeetings(): HashMap<LocalDate, List<MeetingInfo>> {
        val meetingsMap = HashMap<LocalDate, List<MeetingInfo>>()

        // Пример: неделя с 27 марта 2024
        val monday = LocalDate.of(2024, 3, 25)

        // Понедельник, 25 марта
        meetingsMap[monday] = listOf(
            MeetingInfo(
                meeting = Meeting(
                    title = "Старт нового проекта",
                    description = "Обсуждение целей и планов нового проекта",
                    timeStart = LocalDateTime.of(monday, LocalTime.of(11, 0)),
                    duration = Duration.ofMinutes(90),
                    location = "Конференц-зал"
                ),
                invites = listOf(
                    Invite(alex.userId, true),
                    Invite(anna.userId, true),
                    Invite(dmitry.userId, true)
                ),
                participants = listOf(alex, anna, dmitry)
            )
        )

        // Вторник, 26 марта
        val tuesday = monday.plusDays(1)
        meetingsMap[tuesday] = listOf(
            MeetingInfo(
                meeting = Meeting(
                    title = "Обучение команды",
                    description = "Обучение новой технологии",
                    timeStart = LocalDateTime.of(tuesday, LocalTime.of(14, 30)),
                    duration = Duration.ofMinutes(120),
                    location = "Онлайн"
                ),
                invites = listOf(
                    Invite(maria.userId, true),
                    Invite(ivan.userId, true),
                    Invite(sofia.userId, true)
                ),
                participants = listOf(maria, ivan, sofia)
            )
        )

        // Среда, 27 марта
        val wednesday = monday.plusDays(2)
        // Нет встреч в среду

        // Четверг, 28 марта
        val thursday = monday.plusDays(3)
        meetingsMap[thursday] = listOf(
            MeetingInfo(
                meeting = Meeting(
                    title = "Презентация отчета",
                    description = "Презентация квартального отдела",
                    timeStart = LocalDateTime.of(thursday, LocalTime.of(10, 0)),
                    duration = Duration.ofMinutes(60),
                    location = "Зал заседаний"
                ),
                invites = listOf(
                    Invite(alex.userId, true),
                    Invite(anna.userId, true),
                    Invite(ekaterina.userId, true),
                    Invite(mikhail.userId, true)
                ),
                participants = listOf(alex, anna, ekaterina, mikhail)
            ),
            MeetingInfo(
                meeting = Meeting(
                    title = "Индивидуальная встреча",
                    description = "Обсуждение карьерного роста",
                    timeStart = LocalDateTime.of(thursday, LocalTime.of(16, 0)),
                    duration = Duration.ofMinutes(45),
                    location = "Кабинет 305"
                ),
                invites = listOf(
                    Invite(maria.userId, true),
                    Invite(alex.userId, true)
                ),
                participants = listOf(maria, alex)
            )
        )

        // Пятница, 29 марта
        val friday = monday.plusDays(4)
        meetingsMap[friday] = listOf(
            MeetingInfo(
                meeting = Meeting(
                    title = "Завершение спринта",
                    description = "Подведение итогов недели",
                    timeStart = LocalDateTime.of(friday, LocalTime.of(17, 0)),
                    duration = Duration.ofMinutes(60),
                    location = "Переговорная"
                ),
                invites = listOf(
                    Invite(alex.userId, true),
                    Invite(maria.userId, true),
                    Invite(ivan.userId, true)
                ),
                participants = listOf(alex, maria, ivan)
            )
        )

        return meetingsMap
    }
}

val fakeData = HardcodedWeekData.getWeekMeetings()

object MeetingRepositoryImplST: MeetingRepository {
    override suspend fun getMeetingsInfo(): Map<LocalDate, List<MeetingInfo>> {
        delay(2000)
        return fakeData
    }
}