@echo off
setlocal

@REM maven archetype groupId
set ARCHETYPE_GROUP_ID=com.github.gn5r
@REM maven archetype artifactId
set ARCHETYPE_ARTIFACT_ID=gn5r-boot-archetype
@REM maven archetype version
set ARCHETYPE_VERSION=1.3.0-SNAPSHOT

@REM �������n����Ă��邩�`�F�b�N
@if not "%1" == "" (
  @REM �^�[�Q�b�g�f�B���N�g�������݂��邩�`�F�b�N
  @if not exist "%~dp1%1" (
    echo �w�肵���f�B���N�g�������݂��܂���: %~dp1%1
    exit /b
  )
  @REM maven���s���邽�߃J�����g�f�B���N�g�����ړ�
  cd %~dp1%1
) else (
  @REM �f�B���N�g���Ɏw�肪������΃R�}���h�����s���Ă���f�B���N�g���Ɉړ�
  cd %~dp1
)

echo maven�R�}���h�����s���܂�...
mvn archetype:generate -DarchetypeGroupId=%ARCHETYPE_GROUP_ID% -D archetypeArtifactId=%ARCHETYPE_ARTIFACT_ID% -D archetypeVersion=%ARCHETYPE_VERSION%

endlocal