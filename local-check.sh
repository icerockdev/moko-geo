set -e

log() {
  echo "\033[0;32m> $1\033[0m"
}

# run gradle tasks in faster order to receive faster feedback
./gradlew detekt
log "runtime detekt success"

./gradlew assembleDebug
log "runtime android success"

./gradlew compileKotlinIosX64
log "runtime ios success"

./gradlew build publishToMavenLocal
log "runtime build and publish success"
