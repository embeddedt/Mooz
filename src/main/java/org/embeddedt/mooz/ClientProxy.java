package org.embeddedt.mooz;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MouseFilter;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy {
    KeyBinding zoomKey = new KeyBinding("key.mooz.zoom", Keyboard.KEY_C, "key.mooz");
    boolean isZooming = false;
    float oldFov;
    boolean oldSmoothCam = false;

    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        FMLCommonHandler.instance().bus().register(this);
        ClientRegistry.registerKeyBinding(zoomKey);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase != TickEvent.Phase.START)
            return;
        Minecraft mc = Minecraft.getMinecraft();
        boolean nowZooming = (mc.currentScreen == null && zoomKey.getIsKeyPressed());
        if(isZooming != nowZooming) {
            isZooming = nowZooming;
            if(isZooming) {
                oldFov = mc.gameSettings.fovSetting;
                mc.gameSettings.fovSetting = 30;
                oldSmoothCam = mc.gameSettings.smoothCamera;
                mc.gameSettings.smoothCamera = false;
                ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, 0, "field_78498_J", "smoothCamFilterX");
                ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, 0, "field_78499_K", "smoothCamFilterY");
                ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, 0, "field_78496_H", "smoothCamYaw");
                ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, 0, "field_78497_I", "smoothCamPitch");
                ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, 0, "field_78492_L", "smoothCamPartialTicks");
                ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, new MouseFilter(), "field_78527_v", "mouseFilterXAxis");
                ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, new MouseFilter(), "field_78526_w", "mouseFilterYAxis");
                mc.gameSettings.mouseSensitivity /= 2f;
            } else {
                mc.gameSettings.fovSetting = oldFov;
                mc.gameSettings.smoothCamera = oldSmoothCam;
                mc.gameSettings.mouseSensitivity *= 2f;
            }
        }
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes."
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    // postInit "Handle interaction with other mods, complete your setup based on this."
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        super.serverAboutToStart(event);
    }

    // register server commands in this event handler
    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
    }

    public void serverStarted(FMLServerStartedEvent event) {
        super.serverStarted(event);
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        super.serverStopping(event);
    }

    public void serverStopped(FMLServerStoppedEvent event) {
        super.serverStopped(event);
    }
}