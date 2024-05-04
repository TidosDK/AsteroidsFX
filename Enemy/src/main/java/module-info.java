import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;

module Enemy {
    requires Common;
    requires CommonBullet;
    requires CommonPlayer;
    requires CommonEnemy;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    provides IGamePluginService with EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
}
