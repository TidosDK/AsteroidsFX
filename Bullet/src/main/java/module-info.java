import dk.sdu.mmmi.cbse.bulletsystem.Bullet;
import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityCircleCollision;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Bullet {
    requires Common;
    requires CommonBullet;
    provides IGamePluginService with BulletPlugin;
    provides BulletSPI with BulletControlSystem;
    provides IEntityProcessingService with BulletControlSystem;
    provides IEntityCircleCollision with Bullet;
}