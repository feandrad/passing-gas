package io.felipeandrade.passinggas.fabric

import net.fabricmc.api.ClientModInitializer

class PassingGasFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        // ColorProviderRegistry.ITEM.register(
        //     { stack, tintIndex ->
        //         if (tintIndex == 0) 0xFFFFFF else -1
        //     },
        //     PassingGas.MILK_BOTTLE.get()
        // )
    }
}
