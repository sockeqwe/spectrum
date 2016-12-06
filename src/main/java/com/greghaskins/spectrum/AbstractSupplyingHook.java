package com.greghaskins.spectrum;

import static com.greghaskins.spectrum.Spectrum.assertSpectrumInTestMode;

/**
 * A base class for supplying hooks to use. Override before or after. Return the singleton
 * value from the before method.
 * You can use this to write any plugin which needs to make a value visible to the specs.
 * This is not the only way to achieve that - you can also build from {@link SupplyingHook}
 * but this captures the template for a complex hook.
 */
public abstract class AbstractSupplyingHook<T> extends Variable<T> implements SupplyingHook<T> {
  /**
   * Override this to supply behaviour for before the block is run.
   * @return the value that the singleton will store to supply
   */
  protected abstract T before();

  /**
   * Override this to supply behaviour for after the block is run.
   */
  protected void after() {}

  /**
   * Template method for a hook which supplies.
   * @param block the inner block that will be run
   * @throws Throwable on error
   */
  @Override
  public void acceptOrThrow(Block block) throws Throwable {
    try {
      set(before());
      block.run();
    } finally {
      try {
        after();
      } finally {
        clear();
      }
    }
  }

  @Override
  public T get() {
    assertSpectrumInTestMode();

    return super.get();
  }

  private void clear() {
    set(null);
  }
}