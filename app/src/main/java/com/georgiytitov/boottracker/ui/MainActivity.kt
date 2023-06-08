package com.georgiytitov.boottracker.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.georgiytitov.boottracker.R
import com.georgiytitov.boottracker.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BootEventViewModel
    private var hasNotificationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[BootEventViewModel::class.java]

        viewModel.bootEvents.observe(this) {
            binding.bootEventTextView.text = it
        }
    }

    override fun onResume() {
        super.onResume()
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                hasNotificationPermissionGranted = true
            }

            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                showLinkToSettings()
            }

            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    hasNotificationPermissionGranted = true
                }
            }
        }
        viewModel.runNotificationWorker(hasNotificationPermissionGranted)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasNotificationPermissionGranted = isGranted
        if (isGranted) {
            Toast.makeText(
                applicationContext,
                getString(R.string.notification_permission_granted),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.runNotificationWorker(hasNotificationPermissionGranted)
        } else {
            showPermissionNeededDialog()
        }
    }

    private fun showLinkToSettings() {
        Snackbar.make(
            binding.root,
            getString(R.string.notification_blocked),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.settings)) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }.show()
    }

    private fun showPermissionNeededDialog() {
        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle(getString(R.string.notification_permission))
            .setMessage(getString(R.string.notification_permission_is_required))
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }
}