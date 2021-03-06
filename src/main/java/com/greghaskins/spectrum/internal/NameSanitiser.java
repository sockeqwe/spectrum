package com.greghaskins.spectrum.internal;

import java.util.HashSet;
import java.util.Set;

/**
 * Sanitises names within a Suite. To stop a name being duplicated or
 * containing characters that upset test runners.
 */
public class NameSanitiser {
  private Set<String> namesUsed = new HashSet<>();

  /**
   * Deduplicate the given {@code name} and filter out any bad characters.
   *
   * <p>Note: this function has side effects - sanitising a name will cause it to be remembered for future
   * deduplication purposes.
   *
   * @param name the spec name
   * @return a name unique to this sanitiser which has known bad characters removed.
   */
  public String sanitise(final String name) {
    String sanitised = name.replaceAll("\\(", "[")
        .replaceAll("\\)", "]");

    sanitised = sanitised.replaceAll("\\.", "_");

    int suffix = 1;
    String deDuplicated = sanitised;
    while (this.namesUsed.contains(deDuplicated)) {
      deDuplicated = sanitised + "_" + suffix++;
    }
    this.namesUsed.add(deDuplicated);

    return deDuplicated;
  }
}
