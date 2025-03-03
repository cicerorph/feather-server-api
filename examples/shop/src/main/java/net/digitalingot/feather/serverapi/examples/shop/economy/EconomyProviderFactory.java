package net.digitalingot.feather.serverapi.examples.shop.economy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Factory for creating EconomyProvider instances */
public class EconomyProviderFactory {

  private EconomyProviderFactory() {
    throw new AssertionError();
  }

  /**
   * Creates an economy provider based on the available systems Will try to use Vault if available,
   * otherwise falls back to a mock implementation
   *
   * @param plugin The plugin instance
   * @param defaultMockBalance The default balance for the mock provider (if used)
   * @return An EconomyProvider implementation
   */
  public static @NotNull EconomyProvider createProvider(
      @NotNull Plugin plugin, double defaultMockBalance) {
    // Check if Vault is available
    if (plugin.getServer().getPluginManager().getPlugin("Vault") != null) {
      EconomyProvider vaultProvider = createVaultProvider(plugin);

      if (vaultProvider != null && vaultProvider.isInitialized()) {
        plugin.getLogger().info("Using Vault economy integration");
        return vaultProvider;
      }

      plugin
          .getLogger()
          .warning("Vault found but no economy plugin detected. Falling back to mock economy.");
    } else {
      plugin.getLogger().warning("Vault not found. Using mock economy implementation.");
    }

    // Fall back to mock implementation
    plugin.getLogger().info("Using mock economy with default balance of " + defaultMockBalance);
    return new MockEconomyProvider(defaultMockBalance);
  }

  /**
   * Creates an economy provider with a default mock balance of 1000.0
   *
   * @param plugin The plugin instance
   * @return An EconomyProvider implementation
   */
  public static @NotNull EconomyProvider createProvider(@NotNull Plugin plugin) {
    return createProvider(plugin, 1000.0);
  }

  /**
   * Attempts to create a VaultEconomyProvider instance using reflection. This method is used
   * internally to load the Vault integration dynamically.
   *
   * @param plugin The plugin instance needed for initializing the VaultEconomyProvider
   * @return A VaultEconomyProvider instance if successful, or null if any exception occurs during
   *     creation
   */
  private static @Nullable EconomyProvider createVaultProvider(@NotNull Plugin plugin) {
    try {
      String className =
          EconomyProviderFactory.class.getPackage().getName() + ".VaultEconomyProvider";

      Class<?> clazz = Class.forName(className);
      Constructor<?> constructor = clazz.getDeclaredConstructor(Plugin.class);

      return (EconomyProvider) constructor.newInstance(plugin);
    } catch (ClassNotFoundException
        | NoSuchMethodException
        | InstantiationException
        | IllegalAccessException
        | InvocationTargetException error) {
      plugin
          .getLogger()
          .severe(
              "Failed to initialize Vault economy provider: "
                  + (error.getCause() != null
                      ? error.getCause().getMessage()
                      : error.getMessage()));
      return null;
    }
  }
}
