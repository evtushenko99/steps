# Базовый образ для сборки Android приложния
FROM antonkazakov/otus:0.1.0

# Установка переменных среды
ENV VERSION_GRADLE_PROFILER="0.20.0"
ENV VERSION_CMD_LINE_TOOLS="8512546_latest"

ENV ANDROID_SDK_ROOT="/sdk"
ENV GRADLE_PROFILER="/gradle-profiler"
ENV CMD_LINE_TOOLS="${ANDROID_SDK_ROOT}/cmdline-tools"

# Загружаем gradle-profile
RUN mkdir -p ${GRADLE_PROFILER} \
    && cd ${GRADLE_PROFILER} \
    && wget -O GradleProfiler.zip https://repo1.maven.org/maven2/org/gradle/profiler/gradle-profiler/${VERSION_GRADLE_PROFILER}/gradle-profiler-${VERSION_GRADLE_PROFILER}.zip \
    && unzip GradleProfiler.zip -d ${GRADLE_PROFILER}\
    && rm -v GradleProfiler.zip

# Загружаем Android SDK Command Line Tools - инструменты командной строки
RUN cd ${ANDROID_SDK_ROOT} \
    && wget -O Commandlinetools.zip https://dl.google.com/android/repository/commandlinetools-linux-$VERSION_CMD_LINE_TOOLS.zip  \
    && unzip Commandlinetools.zip -d ${ANDROID_SDK_ROOT}\
    && rm -v Commandlinetools.zip

# Принимаем лицензии
RUN mkdir -p "${CMD_LINE_TOOLS}/licenses" \
    && echo "24333f8a63b6825ea9c5514f83c2829b004d1" > "${CMD_LINE_TOOLS}/licenses/android-sdk-license" \
    && echo "84831b9409646a918e30573bab4c9c91346d8" > "${CMD_LINE_TOOLS}/licenses/android-sdk-preview-license"

RUN echo "export ANDROID_SDK_ROOT=$ANDROID_SDK_ROOT" | cat - ~/.bashrc >> temp && mv temp ~/.bashrc

# Переключаемся на пользователя jenkins
User jenkins










