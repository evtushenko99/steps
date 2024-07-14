FROM openjdk:17-jdk-slim

# Установка переменных среды
ENV VERSION_GRADLE_PROFILER="0.20.0"
ENV VERSION_CMD_LINE_TOOLS="8512546_latest"

ENV ANDROID_SDK_ROOT="/sdk"
ENV GRADLE_PROFILER="/gradle-profiler"
ENV CMD_LINE_TOOLS="${ANDROID_SDK_ROOT}/cmdline-tools"

ENV DEBIAN_FRONTEND noninteractive
ENV LANG 'en_US.UTF-8'

RUN dpkg --add-architecture i386 && apt-get update -yqq  \
  && apt-get install -y \
  curl \
  expect \
  git \
  make \
  git-core \
  libc6:i386 \
  libgcc1:i386 \
  libncurses5:i386 \
  libstdc++6:i386 \
  zlib1g:i386 \
  openjdk-17-jdk \
  wget \
  unzip \
  vim \
  openssh-client \
  locales \
  && apt-get clean \
  && rm -rf /var/lib/apt/lists/* \
  && localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8

RUN groupadd -g 1000 -r jenkins && \
  groupadd -g 999 -r docker && \
  groupadd -g 994 -r dockerroot && \
  useradd --create-home -d "/home/jenkins" -u 1000 -r -g jenkins -G docker,dockerroot jenkins

RUN mkdir ${ANDROID_SDK_ROOT}
RUN chown -R jenkins ${ANDROID_SDK_ROOT}

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
RUN yes | ${ANDROID_SDK_ROOT}/cmdline-tools/bin/sdkmanager --licenses --sdk_root=$ANDROID_SDK_ROOT

RUN echo "export ANDROID_SDK_ROOT=$ANDROID_SDK_ROOT" | cat - ~/.bashrc >> temp && mv temp ~/.bashrc

# Переключаемся на пользователя jenkins
User jenkins










