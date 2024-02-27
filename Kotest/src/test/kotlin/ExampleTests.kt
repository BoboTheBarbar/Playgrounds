
//https://kotest.io/docs/framework/testing-styles.html#behavior-spec
import io.kotest.core.spec.style.BehaviorSpec

class ExampleTests : BehaviorSpec({
    given("a broomstick") {
        and("a witch") {
            `when`("The witch sits on it") {
                and("she laughs hysterically") {
                    then("She should be able to fly") {
                        println("Wheeeeeee!")
                    }
                }
            }
        }
    }

    given("a carpet") {
        and("a witch") {
            `when`("The witch sits on it") {
                and("she laughs hysterically") {
                    then("Nothing happens") {
                        println("How boring...")
                    }
                }
            }
        }
    }
})