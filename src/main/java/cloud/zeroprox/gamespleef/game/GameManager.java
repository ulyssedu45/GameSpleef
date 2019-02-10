package cloud.zeroprox.gamespleef.game;

import org.spongepowered.api.entity.living.player.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameManager {

    public List<IGame> iGames = new ArrayList<>();
    private HashMap<UUID, Long> winningCooldowns = new HashMap<>();

    public boolean isPlayerActive(Player player) {
        return getPlayerGame(player).isPresent();
    }

    public Optional<IGame> getGame(String gameName) {
        return iGames.stream().filter(game -> game.getName().equalsIgnoreCase(gameName)).findFirst();
    }

    public Optional<IGame> getPlayerGame(Player player) {
        return iGames.stream().filter(game -> game.getAllPlayers().contains(player.getUniqueId())).findFirst();
    }

    public String getDefaultName() {
        return iGames.size() == 0 ? "DEFAULT" : iGames.get(0).getName();
    }

    public Optional<IGame> getGameFromRegion(Player player) {
        return iGames.stream().filter(game -> game.isInsideArea(player.getLocation())).findFirst();
    }

    public boolean hasCooldown(UUID uniqueId, int winningCooldown) {
        return winningCooldowns.containsKey(uniqueId) && winningCooldowns.get(uniqueId) + TimeUnit.MINUTES.toMillis(winningCooldown) >= System.currentTimeMillis();
    }

    public void setCooldown(UUID uniqueId) {
        winningCooldowns.put(uniqueId, System.currentTimeMillis());
    }
}
