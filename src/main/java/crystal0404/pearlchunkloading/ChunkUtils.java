package crystal0404.pearlchunkloading;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;

import java.util.Comparator;

public class ChunkUtils {
    public static final ChunkTicketType<ChunkPos> ENDER_PEARL = ChunkTicketType.create(
            "ender_pearl",
            Comparator.comparingLong(ChunkPos::toLong),
            10
    );

    public static int getSectionCoordFloored(double coord) {
        return MathHelper.floor(coord) >> 4;
    }

    public static int getSectionCoord(int coord) {
        return coord >> 4;
    }

    public static long addEnderPearlTicket(ServerWorld world, ChunkPos chunkPos) {
        world.getChunkManager().addTicket(ENDER_PEARL, chunkPos, 2, chunkPos);
        return ENDER_PEARL.getExpiryTicks();
    }
}
