package com.hazebyte.crate.cratereloaded.dagger;

import com.hazebyte.crate.cratereloaded.claim.ClaimExecutor;
import com.hazebyte.crate.cratereloaded.component.AnimationFactoryComponent;
import com.hazebyte.crate.cratereloaded.component.ClaimCrateComponent;
import com.hazebyte.crate.cratereloaded.component.ConfigServiceComponent;
import com.hazebyte.crate.cratereloaded.component.EffectResolverComponent;
import com.hazebyte.crate.cratereloaded.component.EffectServiceComponent;
import com.hazebyte.crate.cratereloaded.component.ExportComponent;
import com.hazebyte.crate.cratereloaded.component.GenerateCratePrizeComponent;
import com.hazebyte.crate.cratereloaded.component.GiveCrateComponent;
import com.hazebyte.crate.cratereloaded.component.GivePlayerItemsComponent;
import com.hazebyte.crate.cratereloaded.component.OpenCrateAdminMenuComponent;
import com.hazebyte.crate.cratereloaded.component.OpenCrateComponent;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.component.PreviewCrateComponent;
import com.hazebyte.crate.cratereloaded.component.impl.FilePluginSettingComponentImpl;
import com.hazebyte.crate.cratereloaded.component.RateLimitServiceComponent;
import com.hazebyte.crate.cratereloaded.component.RewardServiceComponent;
import com.hazebyte.crate.cratereloaded.component.SupplyChestCreateComponent;
import com.hazebyte.crate.cratereloaded.crate.BlockCrateHandler;
import com.hazebyte.crate.cratereloaded.crate.CrateHandler;
import com.hazebyte.crate.cratereloaded.crate.animationV2.AnimationManager;
import com.hazebyte.crate.cratereloaded.listener.ListenerManager;
import com.hazebyte.crate.cratereloaded.menuV2.InventoryHistoryManager;
import com.hazebyte.crate.cratereloaded.menuV2.InventoryManager;
import com.hazebyte.crate.cratereloaded.menuV2.InventoryManagerListener;
import com.hazebyte.crate.cratereloaded.parser.YamlCrateV2ParserImpl;
import com.hazebyte.crate.exception.ExceptionHandler;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(
        modules = {
            JavaPluginModule.class,
            ComponentModule.class,
            BukkitListenerModule.class,
        })
public interface JavaPluginComponent {

    ConfigServiceComponent getConfigManagerComponent();

    RateLimitServiceComponent getRateLimitManagerComponent();

    EffectServiceComponent getEffectServiceComponent();

    ClaimCrateComponent getClaimCrateComponent();

    ExportComponent getExportComponent();

    GenerateCratePrizeComponent getGenerateCratePrizeComponent();

    RewardServiceComponent getGenerateRewardComponent();

    GiveCrateComponent getGiveCrateComponent();

    GivePlayerItemsComponent getGivePlayerItemsComponent();

    OpenCrateComponent getOpenCrateComponent();

    PluginSettingComponent getPluginSettingComponent();

    FilePluginSettingComponentImpl getFilePluginSettingComponentImpl();

    PreviewCrateComponent getPreviewCrateComponent();

    SupplyChestCreateComponent getSupplyChestCreateComponent();

    InventoryManager getInventoryManager();

    InventoryHistoryManager getInventoryHistoryManager();

    InventoryManagerListener getInventoryManagerListener();

    AnimationManager getAnimationManager();

    OpenCrateAdminMenuComponent provideOpenCrateAdminMenu();

    CrateHandler getCrateHandler();

    BlockCrateHandler getBlockCrateHandler();

    ListenerManager getListenerManager();

    ClaimExecutor getClaimExecutor();

    YamlCrateV2ParserImpl getYamlCrateV2Parser();

    AnimationFactoryComponent getAnimationFactoryComponent();

    EffectResolverComponent getEffectResolverComponent();

    ExceptionHandler getExceptionHandler();
}
