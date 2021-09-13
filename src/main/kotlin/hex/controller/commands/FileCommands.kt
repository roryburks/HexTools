package hex.controller.commands

import hex.controller.haccess.HexAccessControllerProvider
import hex.controller.haccess.IHexAccessController
import hex.core.di.DiHexCore
import hex.core.haccess.HexAccessFactoryProvider
import hex.core.haccess.IHexAccessFactory

class FileCommands(
    private val _accessFactory: IHexAccessFactory,
    private val _hexAccessController: IHexAccessController
) {
    fun init() {
        makeFileCommand("Open") {svc, _ ->
            val open = svc.dialog.pickFile() ?: return@makeFileCommand
            val access = _accessFactory.getAccess(open) ?: return@makeFileCommand
            _hexAccessController.setAccess(access)
        }

    }

    private fun makeFileCommand(cmd: String, lambda: (CommandSvcSet, Any?)->Unit) {
        CommandRunnerMap.map["File.$cmd"] = lambda
    }
}

object FileCommandsProvider {
    val commands by lazy { FileCommands(
        HexAccessFactoryProvider.factory,
        HexAccessControllerProvider.controller
    ) }

}