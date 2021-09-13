package hex.controller.commands

import hex.core.di.DiHexCore
import hex.core.di.IDialog

interface ICommandRunner {
    fun runCommand( command: String, obj: Any?)
}

class GlobalCommandRunner(
    private val _dialog: IDialog
) : ICommandRunner {
    override fun runCommand(command: String, obj: Any?) {
        CommandRunnerMap.map[command]?.invoke(makeSvcSet(), obj)
    }

    fun makeSvcSet() = CommandSvcSet(_dialog)
}

data class CommandSvcSet(
    val dialog: IDialog
)


object CommandRunnerMap {
    val map = mutableMapOf<String, (CommandSvcSet, Any?)->Unit>()

    fun init() {
        FileCommandsProvider.commands.init()
    }
}

object GlobalCommandRunnerProvider {
    val runner by lazy {
        CommandRunnerMap.init()
        GlobalCommandRunner(DiHexCore.dialog)
    }
}