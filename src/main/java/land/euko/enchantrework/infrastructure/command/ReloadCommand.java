package land.euko.enchantrework.infrastructure.command;

import land.euko.enchantrework.Main;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ReloadCommand implements CommandExecutor {

    private final Main plugin;

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        // Проверка прав доступа
        if (!sender.hasPermission("enchantrework.reload")) {
            sender.sendMessage(ChatColor.RED + "У вас нет прав для выполнения этой команды!");
            return true;
        }

        // Если нет аргументов или неверный аргумент
        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(ChatColor.YELLOW + "Использование: /enchantrework reload");
            return true;
        }

        // Перезагрузка конфигурации
        try {
            plugin.reloadPluginConfig();
            sender.sendMessage(ChatColor.GREEN + "Конфигурация EnchantmentRework успешно перезагружена!");
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Ошибка при перезагрузке конфигурации!");
            plugin.getLogger().severe("Ошибка при перезагрузке конфигурации: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }
}