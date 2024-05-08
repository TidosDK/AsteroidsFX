import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;

module Player {
    requires Common;
    provides IGamePluginService with PlayerPlugin;
}
