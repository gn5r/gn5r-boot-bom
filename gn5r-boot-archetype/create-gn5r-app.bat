@echo off
setlocal

@REM maven archetype groupId
set ARCHETYPE_GROUP_ID=com.github.gn5r
@REM maven archetype artifactId
set ARCHETYPE_ARTIFACT_ID=gn5r-boot-archetype
@REM maven archetype version
set ARCHETYPE_VERSION=1.3.0-SNAPSHOT

@REM 引数が渡されているかチェック
@if not "%1" == "" (
  @REM ターゲットディレクトリが存在するかチェック
  @if not exist "%~dp1%1" (
    echo 指定したディレクトリが存在しません: %~dp1%1
    exit /b
  )
  @REM maven実行するためカレントディレクトリを移動
  cd %~dp1%1
) else (
  @REM ディレクトリに指定が無ければコマンドを実行しているディレクトリに移動
  cd %~dp1
)

echo mavenコマンドを実行します...
mvn archetype:generate -DarchetypeGroupId=%ARCHETYPE_GROUP_ID% -D archetypeArtifactId=%ARCHETYPE_ARTIFACT_ID% -D archetypeVersion=%ARCHETYPE_VERSION%

endlocal